package com.fitee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InvalidHeaderException extends RuntimeException {

    private final String header;
    private final String message;

    public InvalidHeaderException(String message, String header) {
        super(String.format("%s: %s", message, header));
        this.message = message;
        this.header = header;
    }
}
