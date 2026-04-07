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

/**
 * Этот класс отвечает за создание и проверку JWT (JSON Web Token),
 * которые используются для аутентификации пользователей без сессий.
 * Генерирует токены после успешного логина/регистрации
 * <p>
 * Клиент получает «ключ доступа» для защищённых запросов
 * Проверяет валидность токена на каждом запросе
 * <p>
 * Сервер убеждается, что пользователь тот, за кого себя выдаёт
 * Извлекает данные из токена (username)
 */

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
                .withSubject("User details") // Устанавливает subject — о ком токен (стандартное поле sub)
                .withClaim("username", username) // Добавляет кастомное поле username со значением (это то, что мы потом извлечём)
                .withIssuedAt(new Date()) // Устанавливает время выдачи (iat — issued at)
                .withIssuer("SpringRestAPI") // Указывает, кто выдал токен (iss — issuer)
                .withExpiresAt(expirationDate) // Устанавливает время истечения (exp — expires at)
                .sign(Algorithm.HMAC256(jwtSecret)); // Подписывает токен алгоритмом HMAC-SHA256 с использованием секретного ключа
    }

    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT
                .require(Algorithm.HMAC256(jwtSecret)) // Создаёт верификатор с тем же алгоритмом и секретом, что и при подписи
                .withSubject("User details") // Требует, чтобы в токене был именно этот subject (дополнительная проверка)
                .withIssuer("SpringRestAPI") // Требует, чтобы токен был выдан именно этим приложением
                .build();

        try {
            DecodedJWT jwt = verifier.verify(token); // Проверяет подпись и срок действия. Если что-то не так — выбросит JWTVerificationException
            return jwt.getClaim("username").asString(); // Извлекает значение кастомного поля username
        } catch (JWTVerificationException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            throw e;
        }
    }
}
