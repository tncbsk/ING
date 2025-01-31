package com.ing.creditModule.controller;

import com.ing.creditModule.dto.*;
import com.ing.creditModule.exception.LoanException;
import com.ing.creditModule.service.LoanInstallmentService;
import com.ing.creditModule.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/loan")
public class LoanController {

    private final LoanService loanService;

    private final LoanInstallmentService loanInstallmentService;


    /**
     * This endpoint creates a new loan for a customer based on the provided create loan request.
     *
     * @param createLoanRequest
     * @return
     */
    @PostMapping("/create")
    CreateLoanResponse createLoan(@RequestBody CreateLoanRequest createLoanRequest) {

        try {
            return loanService.createLoan(createLoanRequest);
        } catch (LoanException e) {
            return new CreateLoanResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    /**
     * This endpoint List all loan customer based on the  provided list loan request.
     *
     * @param listLoanRequest
     * @return
     */
    @GetMapping("/list-loan")
    List<LoanDTO> listLoans(@RequestBody ListLoanRequest listLoanRequest) {

        return loanService.getLoanListByParameter(listLoanRequest);
    }

    /**
     * This endpoint List all loan installment for customer based on Loan ID
     *
     * @param loanId
     * @return
     */
    @GetMapping("/list-installment/{id}")
    List<LoanInstallmentDTO> listLoanInstallments(@PathVariable("id") Long loanId) {

        return loanInstallmentService.getLoanInstallmentDTOByLoan(loanId);
    }

    /**
     * This endpoint Pay Loan for customer based on Pay Loan request
     *
     * @param payLoanRequest
     * @return
     */
    @PostMapping("/pay")
    PayLoanResponse payLoan(@RequestBody PayLoanRequest payLoanRequest) {
        return loanService.payLoan(payLoanRequest);
    }

}
