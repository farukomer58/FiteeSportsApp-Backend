package com.fitee.fiteeapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class ExpiredTokenException extends RuntimeException {

    private final String message;

    public ExpiredTokenException(String message) {
        super(message);
        this.message = message;
    }
}
