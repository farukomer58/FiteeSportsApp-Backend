package com.fitee.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.OK)
public class JwtAuthResponse {

    private final String accessToken;
    private final String tokenType;

    public JwtAuthResponse(String accessToken) {
        this.accessToken = accessToken;
        tokenType = "Bearer";
    }
}
