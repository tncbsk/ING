package com.ing.creditModule.exception;

public class LoanInstallmentException extends  RuntimeException{
    /**
     * Loan exception class to handle loan-installment-related errors.
     *
     * @param message
     */
    public LoanInstallmentException(String message) {
        super(message);
    }
}
