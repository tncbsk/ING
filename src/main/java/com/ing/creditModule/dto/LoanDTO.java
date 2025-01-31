package com.ing.creditModule.dto;

import com.ing.creditModule.entity.Customer;
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
public class LoanDTO {

    //TODO burada Customerın tamamını neden geri göndermediğimizi açıkla. İleride sensitive datalar olacağı için sadece gerekli bilgileri set ettik.
    /**
     * The name of the Customer
     */
    private String name;
    /**
     * The surname of the Customer
     */
    private String surname;
    /**
     * The Amount of Loan
     */
    private BigDecimal loanAmount;
    /**
     * The number of installment of Loan
     */
    private int numberOfInstallment;
    /**
     * Create Date for Loan
     */
    private Date createDate;
    /**
     * Check the loan is Paid or not
     */
    private Boolean isPaid;
}
