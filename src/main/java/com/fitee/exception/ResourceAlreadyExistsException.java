package com.fitee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends RuntimeException {

    private final String message;

    public ResourceAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }
}
