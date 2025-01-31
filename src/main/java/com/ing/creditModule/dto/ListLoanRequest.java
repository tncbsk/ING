package com.ing.creditModule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListLoanRequest {

    /**
     * The uniques Id for Customer
     */
    private Long customerId;
    //define Box Type Integer instead of primitive. Because if someone doesn't send numberOfInstallment , numberOfInstallment should be null
    /**
     * The number of installment for
     */
    private Integer numberOfInstallment;
    /**
     * The parameter to filter Loan is paid or not
     */
    private Boolean isPaid;
}
