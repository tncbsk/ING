package com.ing.creditModule.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
public class PayLoanResponse {

    /**
     * The Http Stataus of Pay Loan
     */
    private HttpStatus httpStatus;
    /**
     * The number of paid Installment
     */
    private int paidInstallmentNumber;
    /**
     * The  spending total amount
     */
    private BigDecimal spendTotalAmount;
    /**
     * Check the Loan completely Paid or Not
     */
    private Boolean isLoanCompletelyPaid;
    /**
     * The message of Response
     */
    private String message;

    // Constructor, Getters, Setters
    public PayLoanResponse(HttpStatus httpStatus, int paidInstallmentNumber, BigDecimal spendTotalAmount, Boolean isLoanCompletelyPaid , String message) {
        this.httpStatus = httpStatus;
        this.paidInstallmentNumber = paidInstallmentNumber;
        this.spendTotalAmount = spendTotalAmount;
        this.isLoanCompletelyPaid = isLoanCompletelyPaid;
        this.message = message;

    }
}
