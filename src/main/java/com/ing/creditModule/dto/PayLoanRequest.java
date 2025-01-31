package com.ing.creditModule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayLoanRequest {

    /**
     * The  uniques Id for Loan
     */
    private Long loanId;
    /**
     * The requested amount of Loan
     */
    private BigDecimal amount;
}
