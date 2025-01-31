package com.ing.creditModule.service;

import com.ing.creditModule.Util.LoanProcessUtil;
import com.ing.creditModule.dto.*;
import com.ing.creditModule.entity.Customer;
import com.ing.creditModule.entity.Loan;
import com.ing.creditModule.entity.LoanInstallment;
import com.ing.creditModule.exception.CustomerException;
import com.ing.creditModule.exception.LoanException;
import com.ing.creditModule.exception.LoanInstallmentException;
import com.ing.creditModule.mapper.LoanMapper;
import com.ing.creditModule.repos.LoanRepository;
import com.ing.creditModule.validator.CreateLoanRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LoanService {

    private final CustomerService customerService;

    private final LoanInstallmentService loanInstallmentService;

    private final LoanRepository loanRepository;

    private final CreateLoanRequestValidator createLoanRequestValidator;

    /**
     * This method creates a new loan for a customer based on the provided loan request.
     * Validate  Create Loan Request with Validator
     * @param createLoanRequest
     */
    @Transactional
    public CreateLoanResponse createLoan(CreateLoanRequest createLoanRequest) {

        BindingResult bindingResult = new BeanPropertyBindingResult(createLoanRequest, "createLoanRequest");
        createLoanRequestValidator.validate(createLoanRequest, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder("Validation errors: ");
            bindingResult.getAllErrors().forEach(error -> {
                errorMessages.append(error.getDefaultMessage()).append("; ");
            });

            return new CreateLoanResponse(HttpStatus.BAD_REQUEST, errorMessages.toString());
        }

        final Long customerId = createLoanRequest.getCustomerId();
        final BigDecimal interestRate = createLoanRequest.getInterestRate();
        final Integer numberOfInstallments = createLoanRequest.getNumberOfInstallments();

        final Customer customer = customerService.findByCustomerId(customerId);
        final BigDecimal usedCreditLimit = customer.getUsedCreditLimit();

        final BigDecimal totalLoanAmount = createLoanRequest.getAmount().multiply(BigDecimal.ONE.add(interestRate));
        if (customer.getCreditLimit().compareTo(totalLoanAmount.add(usedCreditLimit)) < 0) {
            throw new LoanException("Customer Limit not enough");
        }

        final Loan loan = Loan.builder()
                .loanAmount(totalLoanAmount)
                .customer(customer)
                .isPaid(Boolean.FALSE)
                .numberOfInstallment(numberOfInstallments)
                .createDate(new Date())
                .build();
        loanRepository.save(loan);
        try {
            loanInstallmentService.createLoanInstallment(loan);
            final BigDecimal updatedCreditLimit = usedCreditLimit.add(totalLoanAmount);
            customer.setUsedCreditLimit(updatedCreditLimit);
            customerService.saveOrUpdateCustomer(customer);
        } catch (CustomerException customerException) {
            throw new LoanException("Can not create Loan" + customerException.getMessage());
        } catch (LoanInstallmentException loanInstallmentException) {
            throw new LoanException("Can not create Loan" + loanInstallmentException.getMessage());
        }
        return new CreateLoanResponse(HttpStatus.OK, "Loan Created");
    }

    /**
     * This endpoint List all loan customer based on the  provided list loan request.
     *
     * @param listLoanRequest
     * @return
     */
    public List<LoanDTO> getLoanListByParameter(ListLoanRequest listLoanRequest) {

        final Long customerId = listLoanRequest.getCustomerId();
        final Integer numberOfInstallment = listLoanRequest.getNumberOfInstallment();
        final Boolean isPaid = listLoanRequest.getIsPaid();
        final List<Loan> loansByCustomerIdAndOtherFields = loanRepository.findLoansByCustomerIdAndOtherFields(customerId, numberOfInstallment, isPaid);

        return LoanMapper.INSTANCE.covertToLoanDto(loansByCustomerIdAndOtherFields);
    }

    /**
     * This endpoint Pay Loan for customer based on Pay Loan request
     *
     * @param payLoanRequest
     */
    @Transactional
    public PayLoanResponse payLoan(PayLoanRequest payLoanRequest) {

        BigDecimal remainingPayment = payLoanRequest.getAmount();
        BigDecimal totalPaidAmount = BigDecimal.ZERO;
        int paidInstallmentsCount = 0;

        final Long loanId = payLoanRequest.getLoanId();
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanException("Loan not found"));

        try {
            final List<LoanInstallment> loanInstallmentList = loanInstallmentService.getUnpaidInstallmentByLoan(loanId);
            for (LoanInstallment loanInstallment : loanInstallmentList) {

                final BigDecimal installmentAmount = loanInstallment.getAmount();
                if (canPayInstallment(loanInstallment, installmentAmount)) {
                    final Date dueDate = loanInstallment.getDueDate();

                    loanInstallment.setPaidAmount(calculatePaidAmount(dueDate, loanInstallment.getAmount()));
                    loanInstallment.setIsPaid(true);
                    remainingPayment = remainingPayment.subtract(installmentAmount);
                    totalPaidAmount = totalPaidAmount.add(installmentAmount);
                    paidInstallmentsCount++;
                }
            }

            final Optional<LoanInstallment> anyInstallmentNotPaid = loanInstallmentList.stream().filter(loanInstallment -> !loanInstallment.getIsPaid()).findAny();

            if (!anyInstallmentNotPaid.isPresent()) {
                loan.setIsPaid(true);
            }
            Customer customer = loan.getCustomer();
            customer.setUsedCreditLimit(customer.getUsedCreditLimit().subtract(totalPaidAmount));
            customerService.saveOrUpdateCustomer(customer);

            loanRepository.save(loan);
            loanInstallmentService.updateLoanInstallmentList(loanInstallmentList);

        } catch (Exception ex) {
            PayLoanResponse payLoanResponse = new PayLoanResponse();
            payLoanResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            payLoanResponse.setMessage(ex.getMessage());
            return payLoanResponse;
        }

        return new PayLoanResponse(HttpStatus.CREATED, paidInstallmentsCount, totalPaidAmount, loan.getIsPaid(), null);
    }

    /**
     * This method check  ,
     * 1)remaining amount is exist  or not
     * 2)Remaining amount is enough to pay the installment amount
     * 3)Instalment due date is applicable to pay.
     *
     * @param loanInstallment
     * @param remainingPayment
     * @return
     */
    private boolean canPayInstallment(LoanInstallment loanInstallment, BigDecimal remainingPayment) {

        boolean isRemainingAmountExist = remainingPayment.compareTo(BigDecimal.ZERO) > 0;
        boolean isAmountCanPaid = remainingPayment.compareTo(loanInstallment.getAmount()) >= 0;
        boolean validDueDate = LoanProcessUtil.isDateBeforeFutureDateByMonths(loanInstallment.getDueDate(), 3);
        return validDueDate && isRemainingAmountExist && isAmountCanPaid;
    }

    /**
     * This method calculate Paid amount with 3 different way.
     * 1) If the installment equals due date , paid amount equals  installment amount
     * 2) If an installment is paid before due date, make a discount equal to installmentAmount*0.001*(number of days before due date
     * 3) If an installment is paid after due date, add a penalty equal to installmentAmount *0.001*(number of days after due date)
     *
     * @param dueDate
     * @param amount
     * @return
     */
    private BigDecimal calculatePaidAmount(Date dueDate, BigDecimal amount) {

        final Long dayDifference = LoanProcessUtil.dayDifference(dueDate);

        if (dayDifference == 0) {
            return amount;
        }
        if (dayDifference > 0) {
            BigDecimal penalty = amount.multiply(BigDecimal.valueOf(0.001)).multiply(BigDecimal.valueOf(dayDifference));
            amount = amount.add(penalty);
        }
        if (dayDifference < 0) {
            BigDecimal discount = amount.multiply(BigDecimal.valueOf(0.001)).multiply(BigDecimal.valueOf(-dayDifference));
            amount = amount.subtract(discount);
        }
        return amount;
    }

}
