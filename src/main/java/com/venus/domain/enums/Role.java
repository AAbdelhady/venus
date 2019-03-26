package com.venus.domain.enums;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role implements GrantedAuthority {

    ANONYMOUS("ROLE_ANONYMOUS"),
    USER("ROLE_USER"),
    ARTIST("ROLE_ARTIST"),
    CUSTOMER("ROLE_CUSTOMER");

    private final String authority;
}
