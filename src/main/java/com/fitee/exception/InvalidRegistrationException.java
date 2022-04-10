package com.fitee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidRegistrationException extends RuntimeException{

    private final String user;
    private final String message;

    public InvalidRegistrationException(String user, String message) {
        super(String.format("User [%s] could not be registered. %s", user, message));
        this.user = user;
        this.message = message;
    }
}
