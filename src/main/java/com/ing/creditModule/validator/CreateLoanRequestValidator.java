package com.ing.creditModule.validator;


import com.ing.creditModule.dto.CreateLoanRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Component
public class CreateLoanRequestValidator implements Validator {



    @Override
    public boolean supports(Class<?> clazz) {
        return CreateLoanRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CreateLoanRequest createLoanRequest = (CreateLoanRequest) target;

        List<Integer> allowedInstallments = List.of(6, 9, 12, 24);

        if (createLoanRequest.getCustomerId() == null) {
            errors.rejectValue("customerId", "id.null", "Customer Id can not be null!!");
        }
        if (createLoanRequest.getAmount() == null) {
            errors.rejectValue("Amount", "amount.null", "Amount can not be null!!");
        }
        final Integer numberOfInstallments = createLoanRequest.getNumberOfInstallments();
        if (numberOfInstallments == null) {
            errors.rejectValue("numberOfInstallments", "numberOfInstallments.null", "Latitude can not be null!!");
        }else{
            if(!allowedInstallments.contains(numberOfInstallments)){
                errors.rejectValue("numberOfInstallments", "numberOfInstallments.null", "Invalid number of installments. Allowed values are 6, 9, 12, 24");
            }
        }

        final BigDecimal interestRate = createLoanRequest.getInterestRate();
        if (interestRate == null) {
            errors.rejectValue("interestRate", "interestRate.null", "InterestRate can not be null!!");
        }else{
            if (interestRate.compareTo(new BigDecimal(0.1)) < 0 || interestRate.compareTo(new BigDecimal(0.5)) > 0) {
                errors.rejectValue("interestRate", "interestRate.null", "Interest rate must be between 0.1 and 0.5");
            }
        }

    }
}
