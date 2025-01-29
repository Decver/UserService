package com.example.userservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }



    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws jakarta.servlet.ServletException, IOException {
        // Получаем токен из запроса
        String token = getTokenFromRequest((HttpServletRequest) request);

        // Если токен есть и он валиден
        if (token != null) {
            // Извлекаем имя пользователя из токена
            String username = jwtTokenProvider.extractUsername(token);

            // Загружаем пользователя из базы данных
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Создаем объект аутентификации
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails((jakarta.servlet.http.HttpServletRequest) request));

            // Устанавливаем аутентификацию в SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Продолжаем цепочку фильтров
        filterChain.doFilter(request, response);
    }

    // Метод для извлечения токена из запроса
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Убираем "Bearer" из токена
        }
        return null;
    }
}