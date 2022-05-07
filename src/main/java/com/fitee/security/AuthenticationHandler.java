package com.fitee.security;


import com.fitee.dto.LoginCredentials;
import com.fitee.exception.InvalidHeaderException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

@Component
public class AuthenticationHandler {

    @Value("${security.header-auth-name}")
    private String headerName;

    @Value("${security.header-basic-prefix}")
    private String basicPrefix;

    @Value("${security.header-bearer-prefix}")
    private String bearerPrefix;

    /**
     * A convenience method that decodes and extracts the login credentials of type 'Basic'
     * contained in the HTTP authorization header field. if not valid throws an InvalidHeaderException
     * indicating that the credentials could not be retrieved from the given request.
     */
    public LoginCredentials resolveBasicCredentials(HttpServletRequest request) {
        String auth = getBasicCredentialsFromRequest(request);
        if (auth != null) {
            auth = new String(Base64.getDecoder().decode(auth));
            String[] credentials = auth.split(":", 2);
            return new LoginCredentials(credentials[0], credentials[1]);
        }
        throw new InvalidHeaderException("Header credentials could not be resolved", request.getHeader(headerName));
    }

    /**
     * A convenience method that retrieves the encoded credentials of type 'Basic' from the HTTP
     * authorization header field.
     */
    public String getBasicCredentialsFromRequest(HttpServletRequest request) {
        String header = request.getHeader(headerName);
        return isValidAuthHeader(header, basicPrefix) ? header.substring(basicPrefix.length()) : null;
    }

    /**
     * A convenience method that retrieves the jwt token of type 'Bearer' from the HTTP authorization
     * header field.
     */
    public String getBearerTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader(headerName);
        return isValidAuthHeader(header, bearerPrefix) ? header.substring(bearerPrefix.length()) : null;
    }

    /**
     * A convenience method that validates an HTTP authorization header field based on a given prefix.
     */
    private boolean isValidAuthHeader(String header, String headerPrefix) {
        return StringUtils.hasText(header) && header.startsWith(headerPrefix);
    }


}
