package com.ing.creditModule.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    /**
     * The unique Id for Customer
     */
    private Long id;
    /**
     * The name for Customer
     */
    private String name;
    /**
     * The surname for Customer
     */
    private String surname;
    /**
     * The credit Limit for customer
     */
    private Double creditLimit;
    /**
     * The used Credit Limit for Customer
     */
    private Double usedCreditLimit;

}
