package com.fitee.security.jwt;


import com.fitee.exception.ExpiredTokenException;
import com.fitee.exception.InvalidTokenException;
import com.fitee.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    @Value("${security.jwt.expiration-access}")
    private long accessExpTime;

    @Value("${security.jwt.key-secret}")
    private String secretKey;

    /**
     * Creates an access token with no sensitive data exposure. Note that HS256 is used as the
     * signing algorithm, but is nowadays not commonly used anymore, because it can be brute-forced
     * and is build upon a single secret key that could be shared among multiple servers which is
     * undesirable. RS256 solves these problems, but is out of the scope of this project.
     */
    public String createAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(generateExpirationTime(accessExpTime, false))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .claim("userId", user.getId())
                .claim("role", user.getRole().getId())
                .signWith(SignatureAlgorithm.HS256, generateSigningKey())
                .compact();
    }

    /**
     * Validates the specified token's signature and throws appropriate exceptions based on
     * its failed validation.
     */
    public Jws<Claims> validateToken(String token) {
        try {
            return parseTokenSignature(token);
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException(e.getMessage());
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new InvalidTokenException(e.getMessage());
        }
    }

    /**
     * Same as {@link JwtProvider#validateToken(String)} with the exception that the mentioned
     * method handles all thrown exceptions properly.
     */
    public Jws<Claims> parseTokenSignature(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
    }

    /**
     * Retrieves all claims from a token.
     */
    public Claims resolveTokenClaims(String token) {
        return parseTokenSignature(token).getBody();
    }

    /**
     * Generates a custom expiration time based on the given arguments.
     */
    private Date generateExpirationTime(long expirationTime, boolean rememberMe) {
        // TODO: extend time if rememberMe == true
        return new Date(System.currentTimeMillis() + expirationTime);
    }

    /**
     * Creates a new secret key instance for use with HMAC-SHA algorithms
     */
    private Key generateSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    /**
     * Creates an verifying token.
     */
    public String createVerifyingToken(String email) {
        return Jwts.builder()
                .claim("email", email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(600).toInstant()))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * Validates the specified token's signature and throws appropriate exceptions based on
     * its failed validation.
     */
    public Object verify(String token) {
        try {
            if (token != null) {
                Claims decodedToken = resolveTokenClaims(token);
                Date expirationDate = decodedToken.getExpiration();
                Date currentDate = new Date();

                if (expirationDate.before(currentDate)) {
                    return null;
                } else {
                    Jws<Claims> claimsJws = Jwts.parser()
                            .setSigningKey(secretKey)
                            .parseClaimsJws(token);
                    return claimsJws.getBody().get("email");
                }
            }
            return null;
        } catch (Exception e) {
            return e;
        }
    }
}

//    public Map<?, ?> readTokenClaims(String token) throws JsonProcessingException {
//        token = token.substring(token.indexOf(".") + 1);
//        token = token.substring(0, token.indexOf("."));
//        String jsonNode = new String(Base64.getDecoder().decode(token));
//
//        var mapper = new ObjectMapper();
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        return mapper.readValue(jsonNode, Map.class);
//    }
