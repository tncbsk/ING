package com.ing.creditModule.exception;

public class CustomerException  extends  RuntimeException{
    /**
     * Custom exception class to handle customer-related errors.
     *
     * @param message
     */
    public CustomerException(String message) {
        super(message);
    }
}
