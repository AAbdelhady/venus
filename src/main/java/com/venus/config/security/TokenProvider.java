package com.venus.config.security;

import java.security.Key;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.venus.domain.entities.user.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import static com.venus.config.security.utils.SecurityUtil.JWT_TTL_SECONDS;

@Component
@Slf4j
public class TokenProvider {

    @Value("${jwt.key}")
    private String SECRET_KEY;

    private Key key;

    @PostConstruct
    public void post() {
        key = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS512.getJcaName());
    }

    String createToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + (JWT_TTL_SECONDS * 1000));

        return Jwts.builder()
                .setSubject(Long.toString(user.getId()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }
}
