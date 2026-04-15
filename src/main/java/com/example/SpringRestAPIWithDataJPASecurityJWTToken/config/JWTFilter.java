package com.example.SpringRestAPIWithDataJPASecurityJWTToken.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.security.JWTUtil;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.services.PersonDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Это фильтр безопасности, который перехватывает каждый HTTP-запрос для проверки аутентификации через JWT.
 * OncePerRequestFilter — это базовый класс Spring для фильтров, который гарантирует,
 * что фильтр выполнится ровно один раз за запрос, даже если в приложении есть форварды или инклюды.
 */
@Component
public class JWTFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTFilter.class);

    private final JWTUtil jwtUtil;
    private final PersonDetailService personDetailService;

    public JWTFilter(JWTUtil jwtUtil, PersonDetailService personDetailService) {
        this.jwtUtil = jwtUtil;
        this.personDetailService = personDetailService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization"); // Извлечение заголовка Authorization

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (token.isBlank()) {
                logger.warn("Empty JWT token in Authorization header");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT token in Bearer Header");
                return; // Прерываем выполнение
            }

            try {
                String username = jwtUtil.validateTokenAndRetrieveClaim(token);
                UserDetails userDetails = personDetailService.loadUserByUsername(username);

                // Пароль не нужен — токен уже доказал аутентичность
                // Создали объект аутентификации
                var authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken); // «Сообщает» Spring Security: «Этот запрос делает аутентифицированный пользователь»

                logger.debug("User authenticated: {}", username);

            } catch (JWTVerificationException e) {
                logger.warn("Invalid JWT token: {}", e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
                return; // Прерываем выполнение
            }
        }

        // Если токен не был предоставлен (публичный эндпоинт) или был валиден — запрос обрабатывается дальше
        filterChain.doFilter(request, response);
    }
}
