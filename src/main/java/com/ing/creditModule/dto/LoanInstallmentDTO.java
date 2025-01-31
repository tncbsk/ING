package com.ing.creditModule.dto;


import com.ing.creditModule.entity.Loan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanInstallmentDTO {

    /**
     * The loan information of Loan installment
     */
    private Loan loan;
    /**
     * The amount of Loan Installment
     */
    private BigDecimal amount;
    /**
     * The paid Amount of Loan Installment
     */
    private BigDecimal paidAmount;
    /**
     * The due date of Loan Installment
     */
    private Date dueDate;
    /**
     * The Payment Date of Loan Installment
     */
    private Date paymentDate;
    /**
    * Check the loan Installment is Paid or not
     */
    private Boolean isPaid;
}
