package com.ing.creditModule.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Builder
@Table(name ="CUSTOMER")
public class Customer {

    public Customer(){

    }

    @Builder
    public Customer(Long id , String name , String surname , BigDecimal creditLimit , BigDecimal usedCreditLimit) {
        this.id = id;
        this.name= name;
        this.surname = surname;
        this.creditLimit = creditLimit;
        this.usedCreditLimit = usedCreditLimit;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * Name of Customer
     */
    private String name;
    /**
     * Surname of Customer
     */
    private String surname;
    /**
     * Credit Limit of Customer
     */
    private BigDecimal creditLimit;
    /**
     * Used Credit Limit of Customer
     */
    private BigDecimal usedCreditLimit;
}
