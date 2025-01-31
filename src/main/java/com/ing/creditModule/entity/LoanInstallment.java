package com.ing.creditModule.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Builder
@Table(name ="LOAN_INSTALLMENT")
public class LoanInstallment {

    public LoanInstallment(){

    }

    @Builder
    public LoanInstallment(Long id , Loan loan , BigDecimal amount , BigDecimal paidAmount , Date dueDate , Date paymentDate ,  Boolean isPaid) {
        this.id = id;
        this.loan= loan;
        this.amount = amount;
        this.paidAmount = paidAmount;
        this.dueDate = dueDate;
        this.paymentDate = paymentDate;
        this.isPaid = isPaid;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;
    /**
     * Amount of Loan Installment
     */
    private BigDecimal amount;
    /**
     * Paid Amount of Loan Installment
     */
    private BigDecimal paidAmount;
    /**
     * Due Date of Loan Installment
     */
    private Date dueDate;
    /**
     * Payment Date of Loan Installment
     */
    private Date paymentDate;

    /**
     * Check the Loan Installment paid or not
     */
    private Boolean isPaid;

}
