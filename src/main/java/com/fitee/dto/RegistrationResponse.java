package com.fitee.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.CREATED)
public class RegistrationResponse {

    private final String username;
    private final String message;

    public RegistrationResponse(String username, String message) {
        this.username = username;
        this.message = message;
    }
}
