package com.ing.creditModule.service;

import com.ing.creditModule.Util.LoanProcessUtil;
import com.ing.creditModule.dto.LoanInstallmentDTO;
import com.ing.creditModule.entity.Loan;
import com.ing.creditModule.entity.LoanInstallment;
import com.ing.creditModule.exception.LoanInstallmentException;
import com.ing.creditModule.mapper.LoanInstallmentMapper;
import com.ing.creditModule.repos.LoanInstallmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanInstallmentService {

    private final LoanInstallmentRepository loanInstallmentRepository;

    /**
     *  The method create a new Loan Installment Base on Loan
     * @param loan
     * @LoanInstallmentException If there a error during the transaction , method raise "Error while during the create Installment, transaction rolled back"
     */
    public void createLoanInstallment(Loan loan) {

        try {
            final int numberOfInstallment = loan.getNumberOfInstallment();
            final List<Date> dates = LoanProcessUtil.generateDueDates(numberOfInstallment);
            BigDecimal loanAmount = LoanProcessUtil.calculateLoanAmount(loan.getLoanAmount(), numberOfInstallment);
            List<LoanInstallment> loanInstallmentList = new ArrayList<>();

            for (int i = 0; i < numberOfInstallment; i++) {

                LoanInstallment loanInstallment = LoanInstallment.builder()
                        .loan(loan)
                        .dueDate(dates.get(i))
                        .isPaid(Boolean.FALSE)
                        .paidAmount(null)
                        .paymentDate(null)
                        .amount(loanAmount)
                        .build();
                loanInstallmentList.add(loanInstallment);
            }
            loanInstallmentRepository.saveAll(loanInstallmentList);

        } catch (Exception e) {
            throw new LoanInstallmentException("Error while during the create Installment, transaction rolled back" + e.getMessage());
        }

    }

    /**
     * This method List all loan installment for customer based on Loan ID
     * @param loanId
     * @return
     */
    public List<LoanInstallmentDTO> getLoanInstallmentDTOByLoan(Long loanId) {

        final List<LoanInstallment> loanInstallmentList = loanInstallmentRepository.findByLoanId(loanId);

        //TODO response'ta hem installment'ın hem de Customer'ın bütün bilgileri dönüyor.
        return LoanInstallmentMapper.INSTANCE.covertToLoanInstallmentDto(loanInstallmentList);
    }

    /**
     * This method Update Loan Installment list with Bulk
     * @param loanInstallmentList
     */
    public void updateLoanInstallmentList(List<LoanInstallment> loanInstallmentList){

        loanInstallmentRepository.saveAll(loanInstallmentList);
    }

    /**
     *This method List all Unpaid Loan Instalment base on Loan Id
     * @param loanId
     * @return
     */
    public List<LoanInstallment> getUnpaidInstallmentByLoan(Long loanId) {

        return loanInstallmentRepository.findByLoanIdAndIsPaid(loanId, Boolean.FALSE);
    }
}
