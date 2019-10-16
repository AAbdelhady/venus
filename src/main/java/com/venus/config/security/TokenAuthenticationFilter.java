package com.venus.config.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.venus.config.security.utils.CookieUtil;
import com.venus.config.security.utils.SecurityUtil;
import com.venus.feature.user.entity.User;
import com.venus.feature.user.repository.UserRepository;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final static String AUTH_HEADER_NAME = "Authorization";

    private final static String TOKEN_PREFIX = "Bearer ";

    private final TokenProvider tokenProvider;

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Optional<String> jwtOptional = getJwtFromCookie(request);
            if (!jwtOptional.isPresent())
                jwtOptional = getJwtFromHeader(request);
            jwtOptional.ifPresent(jwt -> {
                Long userId = tokenProvider.getUserIdFromToken(jwt);
                User user = userRepository.findById(userId).orElseThrow(() -> new MalformedJwtException("No user found for ID: " + userId));
                SecurityUtil.updateCurrentUserContext(user);
            });
        } catch (ExpiredJwtException ex) {
            handleExpiredJwtException(ex);
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token", ex);
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token", ex);
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty", ex);
        }
        filterChain.doFilter(request, response);
    }

    private Optional<String> getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTH_HEADER_NAME);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX))
            return Optional.of(bearerToken.substring(TOKEN_PREFIX.length()));
        return Optional.empty();
    }

    private Optional<String> getJwtFromCookie(HttpServletRequest request) {
        Optional<Cookie> jwtCookie = CookieUtil.getCookie(request, CookieUtil.JWT_COOKIE);
        return jwtCookie.map(Cookie::getValue);
    }

    private void handleExpiredJwtException(ExpiredJwtException ex) {
        String userId = ex.getClaims().getSubject();
        String expirationDt = ex.getClaims().getExpiration().toInstant().toString();
        log.warn("JWT for user with id [{}] expired at {}", userId, expirationDt);
    }
}
