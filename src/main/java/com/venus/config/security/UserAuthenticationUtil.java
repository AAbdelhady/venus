package com.venus.config.security;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.venus.domain.entities.user.User;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

public class UserAuthenticationUtil {

    public static Optional<String> getCurrentUserLoginId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getPrincipal() != null ? Optional.of(auth.getPrincipal().toString()) : Optional.empty();
    }

    public static boolean isUserAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
    }

    public static void updateCurrentUserContext(User user) {
        List<GrantedAuthority> updatedAuthorities = AuthorityUtils.createAuthorityList(user.getRole().getAuthority());
        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(user.getLoginId(), null, updatedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        updateSession(SecurityContextHolder.getContext());
    }

    private static void updateSession(SecurityContext context) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, context);
    }
}
