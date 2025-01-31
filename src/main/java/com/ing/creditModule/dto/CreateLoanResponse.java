package com.ing.creditModule.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
@NoArgsConstructor
public class CreateLoanResponse {

    /**
     * The Http status of Response
     */
    private HttpStatus httpStatus;
    /**
     * The message of Response
     */
    private String message;


    public CreateLoanResponse(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

}

