package com.fitee.service;

import com.fitee.dto.LoginCredentials;
import com.fitee.security.AuthenticationHandler;
import com.fitee.security.Principal;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@AllArgsConstructor
public class AuthService {

    private final AuthenticationHandler authenticationHandler;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * Resolves credentials contained in an HTTP authorization request header of type 'Basic'
     * and authenticates them against the database. If successful returns a fully populated
     * authenticated principal object (including authorities).
     */
    public Principal authenticateBasicCredentials(HttpServletRequest request) {
        final LoginCredentials loginCredentials = authenticationHandler.resolveBasicCredentials(request);
        final UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(loginCredentials.getEmail(), loginCredentials.getPassword());
        final Authentication auth = authenticationManagerBuilder.getObject().authenticate(authToken);
        return (Principal) auth.getPrincipal();
    }
}
