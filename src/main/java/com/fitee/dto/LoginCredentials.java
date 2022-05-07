package com.fitee.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class LoginCredentials {

    @NotEmpty
    private String email;
    @NotEmpty
    private String password;

    public LoginCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
