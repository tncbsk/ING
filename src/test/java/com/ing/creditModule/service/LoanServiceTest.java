package com.ing.creditModule.service;

import com.ing.creditModule.dto.*;
import com.ing.creditModule.entity.Customer;
import com.ing.creditModule.entity.Loan;
import com.ing.creditModule.entity.LoanInstallment;
import com.ing.creditModule.exception.LoanException;
import com.ing.creditModule.exception.LoanInstallmentException;
import com.ing.creditModule.repos.LoanRepository;
import com.ing.creditModule.validator.CreateLoanRequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LoanServiceTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private LoanInstallmentService loanInstallmentService;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private CreateLoanRequestValidator createLoanRequestValidator;


    @InjectMocks
    private LoanService loanService;

    private Customer customer;
    private Loan loan;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer();
        customer.setId(1L);
        customer.setUsedCreditLimit(new BigDecimal(1000));
        customer.setCreditLimit(new BigDecimal(60000));

        loan = new Loan();
        loan.setLoanAmount(new BigDecimal(52500)); // Amount + interest
        loan.setCustomer(customer);
        loan.setNumberOfInstallment(5);
        loan.setCreateDate(new Date());
    }

    @Test
    void testCreateLoan_WithValidRequest_ShouldCreateLoan() {

        CreateLoanRequest createLoanRequest = new CreateLoanRequest();
        createLoanRequest.setCustomerId(1L);
        createLoanRequest.setAmount(new BigDecimal(50000));
        createLoanRequest.setInterestRate(new BigDecimal(0.05));
        createLoanRequest.setNumberOfInstallments(5);

        when(customerService.findByCustomerId(1L)).thenReturn(customer);
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);
        doNothing().when(loanInstallmentService).createLoanInstallment(any(Loan.class));


        CreateLoanResponse response = loanService.createLoan(createLoanRequest);

        assertEquals(HttpStatus.OK, response.getHttpStatus());
        assertEquals("Loan Created", response.getMessage());
        verify(customerService, times(1)).saveOrUpdateCustomer(customer);
        verify(loanRepository, times(1)).save(any(Loan.class));
    }

    @Test
    void testPayLoan_Success_ShouldReturnPaidResponse() {

        PayLoanRequest payLoanRequest = new PayLoanRequest();
        payLoanRequest.setLoanId(1L);
        payLoanRequest.setAmount(new BigDecimal(10000));

        Loan loan = new Loan();
        loan.setId(1L);
        loan.setIsPaid(Boolean.FALSE);
        loan.setLoanAmount(new BigDecimal(50000));
        loan.setCustomer(customer);

        LoanInstallmentDTO installment = new LoanInstallmentDTO();
        installment.setAmount(new BigDecimal(10000));

        LoanInstallment loanInstallment = new LoanInstallment();
        loanInstallment.setAmount(new BigDecimal(10000));
        loanInstallment.setDueDate(new Date());

        when(loanRepository.findById(1L)).thenReturn(java.util.Optional.of(loan));
        when(loanInstallmentService.getUnpaidInstallmentByLoan(1L)).thenReturn(List.of(loanInstallment));
        doNothing().when(loanInstallmentService).updateLoanInstallmentList(anyList());

        PayLoanResponse response = loanService.payLoan(payLoanRequest);
        assertEquals(HttpStatus.CREATED, response.getHttpStatus());
        assertEquals(1, response.getPaidInstallmentNumber());
        assertEquals(new BigDecimal(10000), response.getSpendTotalAmount());
    }

    @Test
    void testPayLoan_WithError_ShouldReturnBadRequest() {

        PayLoanRequest payLoanRequest = new PayLoanRequest();
        payLoanRequest.setLoanId(999L);

        doThrow(new LoanException("Loan not found")).when(loanRepository).findById(100L);
        LoanException exception = assertThrows(LoanException.class, () -> loanService.payLoan(payLoanRequest));
        assertTrue(exception.getMessage().contains("Loan not found"));

    }

    @Test
    void testGetLoanListByParameter_ShouldReturnLoanList() {

        ListLoanRequest listLoanRequest = new ListLoanRequest();
        listLoanRequest.setCustomerId(1L);
        listLoanRequest.setIsPaid(Boolean.FALSE);

        Loan loan = new Loan();
        loan.setLoanAmount(new BigDecimal(50000));
        loan.setIsPaid(Boolean.FALSE);

        when(loanRepository.findLoansByCustomerIdAndOtherFields(1L, null, Boolean.FALSE)).thenReturn(List.of(loan));
        List<LoanDTO> loanDTOs = loanService.getLoanListByParameter(listLoanRequest);
        assertNotNull(loanDTOs);
        assertFalse(loanDTOs.isEmpty());
    }

}