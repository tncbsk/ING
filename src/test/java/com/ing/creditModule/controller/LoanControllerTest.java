package com.ing.creditModule.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing.creditModule.dto.*;
import com.ing.creditModule.entity.Customer;
import com.ing.creditModule.entity.Loan;
import com.ing.creditModule.service.LoanInstallmentService;
import com.ing.creditModule.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private LoanController loanController;

    @Mock
    private LoanInstallmentService loanInstallmentService;

    @Mock
    private LoanService loanService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(loanController).build();
    }


    @Test
    public void testCreateLoan() throws Exception {

        CreateLoanRequest loanRequest = new CreateLoanRequest();
        loanRequest.setCustomerId(1L);
        loanRequest.setAmount(new BigDecimal("1000"));
        loanRequest.setInterestRate(new BigDecimal("0.05"));
        loanRequest.setNumberOfInstallments(12);

        mockMvc.perform(post("/api/loan/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loanRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void listLoans() throws Exception{

        ListLoanRequest listLoanRequest = new ListLoanRequest();
        listLoanRequest.setCustomerId(1L);
        listLoanRequest.setIsPaid(Boolean.FALSE);
        listLoanRequest.setNumberOfInstallment(9);

        LoanDTO loanDTO1 = new LoanDTO();
        loanDTO1.setIsPaid(Boolean.FALSE);
        loanDTO1.setLoanAmount(new BigDecimal(10000));
        loanDTO1.setNumberOfInstallment(6);
        LoanDTO loanDTO2 = new LoanDTO();
        loanDTO2.setIsPaid(Boolean.FALSE);
        loanDTO1.setLoanAmount(new BigDecimal(20000));
        loanDTO1.setNumberOfInstallment(3);
        List<LoanDTO> loanDTOS = Arrays.asList(loanDTO1, loanDTO2);


        when(loanService.getLoanListByParameter(listLoanRequest)).thenReturn(loanDTOS);


        mockMvc.perform(get("/api/loan/list-loan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(listLoanRequest)))
                .andExpect(status().isOk());

    }

    @Test
    void listLoanInstallments() throws Exception{

        Customer customer = new Customer();
        customer.setId(1l);
        customer.setName("Steve");
        customer.setSurname("Nash");
        customer.setCreditLimit(new BigDecimal(60000));
        customer.setUsedCreditLimit(new BigDecimal(1000));
        Loan loan = new Loan();
        loan.setId(1L);
        loan.setCreateDate(new Date());
        loan.setNumberOfInstallment(3);
        loan.setIsPaid(Boolean.FALSE);
        loan.setCustomer(customer);

        LoanInstallmentDTO installment1 = new LoanInstallmentDTO();
        installment1.setLoan(loan);
        installment1.setAmount(new BigDecimal(20000));
        installment1.setIsPaid(Boolean.FALSE);
        installment1.setPaidAmount(null);
        LoanInstallmentDTO installment2 = new LoanInstallmentDTO();
        installment2.setLoan(loan);
        installment2.setAmount(new BigDecimal(20000));
        installment2.setIsPaid(Boolean.FALSE);
        installment2.setPaidAmount(null);

        List<LoanInstallmentDTO> installments = Arrays.asList(installment1, installment2);

        when(loanInstallmentService.getLoanInstallmentDTOByLoan(1L)).thenReturn(installments);

        mockMvc.perform(get("/api/loan/list-installment/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].amount").value(new BigDecimal(20000)))
                .andExpect(jsonPath("$[1].amount").value(new BigDecimal(20000)));

    }


    @Test
    void payLoan()  throws Exception{

        PayLoanRequest payLoanRequest = new PayLoanRequest();
        payLoanRequest.setLoanId(1L);
        payLoanRequest.setAmount(new BigDecimal(10000));

        PayLoanResponse payLoanResponse = new PayLoanResponse();
        payLoanResponse.setMessage(null);
        payLoanResponse.setHttpStatus(HttpStatus.OK);
        payLoanResponse.setPaidInstallmentNumber(2);
        payLoanResponse.setSpendTotalAmount(new BigDecimal(20000));

        when(loanService.payLoan(payLoanRequest)).thenReturn(payLoanResponse);

       mockMvc.perform(post("/api/loan/pay")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(payLoanRequest)))
                .andExpect(status().isOk());



    }
}