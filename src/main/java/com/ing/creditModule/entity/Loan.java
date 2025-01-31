package com.ing.creditModule.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
@Entity
@Builder
@Table(name ="LOAN")
public class Loan {

    public Loan(){

    }

    @Builder
    public Loan(Long id , Customer customer , BigDecimal loanAmount , int numberOfInstallment , Date createDate , Boolean isPaid) {
        this.id = id;
        this.customer= customer;
        this.loanAmount = loanAmount;
        this.numberOfInstallment = numberOfInstallment;
        this.createDate = createDate;
        this.isPaid = isPaid;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    /**
     * Loan Amount
     */
    private BigDecimal loanAmount;
    /**
     * Number of Installment
     */
    private int numberOfInstallment;
    /**
     * Create Date of Loan
     */
    private Date createDate;
    /**
     * Check to Loan is paid or not
     */
    private Boolean isPaid;

}
