package com.example.SpringRestAPIWithDataJPASecurityJWTToken.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    private static final Logger logger = LoggerFactory.getLogger(JWTUtil.class);

    @Value("${jwt_secret}")
    private String jwtSecret;

    @Value("${jwt.expiration:60}")
    private long expirationMinutes;

    public String generateToken(String username) {
        Date expirationDate = new Date(System.currentTimeMillis() + expirationMinutes * 60 * 1000);

        return JWT.create()
                .withSubject("User details")
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withIssuer("SpringRestAPI")
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT
                .require(Algorithm.HMAC256(jwtSecret))
                .withSubject("User details")
                .withIssuer("SpringRestAPI")
                .build();

        try {
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("username").asString();
        } catch (JWTVerificationException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            throw e;
        }
    }
}
