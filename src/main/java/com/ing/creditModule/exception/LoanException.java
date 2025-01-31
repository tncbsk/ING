package com.ing.creditModule.exception;

public class LoanException extends  RuntimeException {

    /**
     * Loan exception class to handle loan-related errors.
     *
     * @param message
     */
    public LoanException(String message) {
        super(message);
    }

}
