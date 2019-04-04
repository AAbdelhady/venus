package com.venus.config.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.venus.domain.entities.user.User;
import com.venus.repositories.UserRepository;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final static String AUTH_HEADER_NAME = "Authorization";

    private final static String TOKEN_PREFIX = "Bearer ";

    private final TokenProvider tokenProvider;

    private final UserRepository userRepository;

    @Autowired
    public TokenAuthenticationFilter(TokenProvider tokenProvider, UserRepository userRepository) {
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Optional<String> jwtOptional = getJwtFromRequest(request);
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

    private Optional<String> getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTH_HEADER_NAME);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX))
            return Optional.of(bearerToken.substring(TOKEN_PREFIX.length()));
        return Optional.empty();
    }

    // should be used with webapps instead of headers
    /*private Optional<String> getJwtFromRequest(HttpServletRequest request) {
        Optional<Cookie> jwtCookie = CookieUtil.getCookie(request, CookieUtil.JWT_COOKIE);
        return jwtCookie.map(Cookie::getValue);
    }*/

    private void handleExpiredJwtException(ExpiredJwtException ex) {
        String userId = ex.getClaims().getSubject();
        String expirationDt = ex.getClaims().getExpiration().toInstant().toString();
        log.warn("JWT for user with id [{}] expired at {}", userId, expirationDt);
    }
}
