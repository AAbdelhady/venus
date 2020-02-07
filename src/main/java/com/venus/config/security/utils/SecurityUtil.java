package com.venus.config.security.utils;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import com.venus.feature.user.entity.User;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SecurityUtil {

    public static final String JWT_HEADER_NAME = "JWT";

    public static final String JWT_TTL_HEADER_NAME = "JWT_TTL_SEC";

    public static final int JWT_TTL_SECONDS = 86400;

    public static Optional<Long> getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getPrincipal() != null ? Optional.of(Long.parseLong(auth.getPrincipal().toString())) : Optional.empty();
    }

    public static void updateCurrentUserContext(User user) {
        List<GrantedAuthority> updatedAuthorities = AuthorityUtils.createAuthorityList(user.getRole().getAuthority());
        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(user.getId(), null, updatedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}
