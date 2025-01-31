package com.ing.creditModule.exception;


import com.ing.creditModule.dto.CreateLoanResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * This Handle can use for another Exception if needed
 */
@ControllerAdvice
public class GenericExceptionHandler {

    /**
     * If there is an exception on  Loan Flow  LoanException will respond with Http Status , Message
     * @param ex
     * @return
     */
    @ExceptionHandler(com.ing.creditModule.exception.LoanException.class)
    public ResponseEntity<CreateLoanResponse> handleLoanException(com.ing.creditModule.exception.LoanException ex) {

        CreateLoanResponse response = new CreateLoanResponse();
        response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        response.setMessage(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * If there is an exception on Customer Flow CustomerException will respond with Http Status , Message
     * @param ex
     * @return
     */
    @ExceptionHandler(com.ing.creditModule.exception.CustomerException.class)
    public ResponseEntity<CreateLoanResponse> handleCustomerException(com.ing.creditModule.exception.CustomerException ex) {

        CreateLoanResponse response = new CreateLoanResponse();
        response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        response.setMessage(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * If there is an exception on Customer Flow CustomerException will respond with Http Status , Message
     * @param ex
     * @return
     */
    @ExceptionHandler(com.ing.creditModule.exception.LoanInstallmentException.class)
    public ResponseEntity<CreateLoanResponse> handleLoanInstallmentException(com.ing.creditModule.exception.LoanInstallmentException ex) {

        CreateLoanResponse response = new CreateLoanResponse();
        response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        response.setMessage(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

