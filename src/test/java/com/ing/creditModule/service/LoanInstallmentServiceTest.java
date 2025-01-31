package com.ing.creditModule.service;

import com.ing.creditModule.dto.LoanInstallmentDTO;
import com.ing.creditModule.entity.Loan;
import com.ing.creditModule.entity.LoanInstallment;
import com.ing.creditModule.exception.LoanInstallmentException;
import com.ing.creditModule.repos.LoanInstallmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanInstallmentServiceTest {

    @Mock
    private LoanInstallmentRepository loanInstallmentRepository;

    @InjectMocks
    private LoanInstallmentService loanInstallmentService;

    private Loan loan;

    private LoanInstallment loanInstallment1;
    private LoanInstallment loanInstallment2;
    private LoanInstallment loanInstallment3;

    @BeforeEach
    void setUp() {

        loan = new Loan();
        loan.setId(1L);
        loan.setNumberOfInstallment(3);
        loan.setLoanAmount(new BigDecimal(60000));

        loanInstallment1 = new LoanInstallment();
        loanInstallment1.setLoan(loan);
        loanInstallment1.setAmount(new BigDecimal(20000));

        loanInstallment2 = new LoanInstallment();
        loanInstallment2.setLoan(loan);
        loanInstallment2.setAmount(new BigDecimal(20000));

        loanInstallment3 = new LoanInstallment();
        loanInstallment3.setLoan(loan);
        loanInstallment3.setAmount(new BigDecimal(20000));
    }

    /**
     * Test: When Create Loan Installment method execute correctly
     */
    @Test
    void testCreateLoanInstallment_Success() {



        List<LoanInstallment> loanInstallmentList = Arrays.asList(loanInstallment1, loanInstallment2,loanInstallment3);


        when(loanInstallmentRepository.saveAll(Mockito.anyList())).thenReturn(loanInstallmentList);

        loanInstallmentService.createLoanInstallment(loan);

        verify(loanInstallmentRepository, times(1)).saveAll(Mockito.anyList());
    }

    /**
     * Test: When Create Loan Installment method execute incorrectly   , need to throw LoanInstallmentException
     */
    @Test
    void testCreateLoanInstallment_Failure() {

        doThrow(new RuntimeException("Database error")).when(loanInstallmentRepository).saveAll(Mockito.anyList());

        LoanInstallmentException exception = assertThrows(LoanInstallmentException.class, () -> loanInstallmentService.createLoanInstallment(loan));

        assertTrue(exception.getMessage().contains("Error while during the create Installment, transaction rolled back"));
    }


    /**
     *Test: When Get  Loan Installment method execute correctly
     */
    @Test
    void testGetLoanInstallmentDTOByLoan_Success() {

        List<LoanInstallment> loanInstallmentList = Arrays.asList(loanInstallment1, loanInstallment2 , loanInstallment3);

        when(loanInstallmentRepository.findByLoanId(1L)).thenReturn(loanInstallmentList);

        List<LoanInstallmentDTO> result = loanInstallmentService.getLoanInstallmentDTOByLoan(1L);

        assertNotNull(result);
        assertEquals(3, result.size());
    }

    /**
     * Test: When Update Loan Installment method execute correctly
     */
    @Test
    void updateLoanInstallmentList() {


        List<LoanInstallment> loanInstallmentList = Arrays.asList(loanInstallment1, loanInstallment2);
        when(loanInstallmentRepository.saveAll(loanInstallmentList)).thenReturn(loanInstallmentList);
        loanInstallmentService.updateLoanInstallmentList(loanInstallmentList);
        verify(loanInstallmentRepository, times(1)).saveAll(loanInstallmentList);
    }

    /**
     * Test: When Get Unpaid Installment Loan method execute correctly
     */
    @Test
    void getUnpaidInstallmentByLoan() {


        List<LoanInstallment> loanInstallmentList = Arrays.asList(loanInstallment1, loanInstallment2 ,loanInstallment3);
        when(loanInstallmentRepository.findByLoanIdAndIsPaid(1L, Boolean.FALSE)).thenReturn(loanInstallmentList);
        List<LoanInstallment> result = loanInstallmentService.getUnpaidInstallmentByLoan(1L);

        assertNotNull(result);
        assertEquals(3, result.size());
    }
}