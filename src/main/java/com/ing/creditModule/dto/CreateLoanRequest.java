package com.ing.creditModule.dto;


import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateLoanRequest {

        /**
         * The unique identifier of the customer
         */
        private Long customerId;
        /**
         * The requested amount
         */
        private BigDecimal amount;
        /**
         * The requested interested Rate
         */
        private BigDecimal interestRate;
        /**
         * The requested number of installment
         */
        private Integer numberOfInstallments;

}
