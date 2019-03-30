package com.venus.config.security;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import com.venus.domain.entities.user.User;

public class UserAuthenticationUtil {

    public static Optional<Long> getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getPrincipal() != null ? Optional.of(Long.parseLong(auth.getPrincipal().toString())) : Optional.empty();
    }

    public static boolean isUserAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
    }

    public static void updateCurrentUserContext(User user) {
        List<GrantedAuthority> updatedAuthorities = AuthorityUtils.createAuthorityList(user.getRole().getAuthority());
        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(user.getId(), null, updatedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}
