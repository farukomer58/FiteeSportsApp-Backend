package com.fitee.fiteeapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InvalidTokenException extends RuntimeException {

    private final String message;

    public InvalidTokenException(String message) {
        super(String.format("Invalid token: %s", message));
        this.message = message;
    }
}
