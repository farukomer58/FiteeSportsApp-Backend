package com.fitee.controller;

import com.fitee.dto.JwtAuthResponse;
import com.fitee.dto.RoleType;
import com.fitee.security.Principal;
import com.fitee.security.jwt.JwtProvider;
import com.fitee.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final JwtProvider jwtProvider;
    private final AuthService authService;

    /**
     * Verifies a new authorization request with given credentials contained inside the
     * authorization header. On successful verification creates an access-token (AT) that is
     * send back to the client allowing access to this server's resources.
     */
    @PostMapping("/token")
    public JwtAuthResponse createAuthTokens(@RequestParam(defaultValue = "false") boolean rememberMe,
                                            HttpServletRequest request) {
        System.out.println("LOGIN????");
        Principal principal = authService.authenticateBasicCredentials(request);
        String accessToken  = jwtProvider.createAccessToken(principal);
        return new JwtAuthResponse(accessToken);
    }

    /**
     * Renews an expired access-token (AT) to continue having access to this server's resources.
     * To comply with modern implementations this should be done with refresh-tokens (RT), but
     * since parallel request handling and additional security features are required for this, I
     * have opted not to use this as it's out of the scope of this project.
     */
    @Secured({RoleType.FREELANCER, RoleType.CUSTOMER})
    @PostMapping("/refresh")
    public JwtAuthResponse renewAuthTokens() {
        final Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String accessToken = jwtProvider.createAccessToken(principal);
        return new JwtAuthResponse(accessToken);
    }

    @GetMapping("/validate")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void validateToken(@RequestHeader("Authorization") String header) {
        System.out.println("validating token controller");
    }
}
