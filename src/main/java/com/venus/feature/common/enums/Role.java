package com.venus.feature.common.enums;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role implements GrantedAuthority {

    ANONYMOUS("ROLE_ANONYMOUS"),
    UNSPECIFIED("ROLE_UNSPECIFIED"),
    ARTIST("ROLE_ARTIST"),
    CUSTOMER("ROLE_CUSTOMER");

    private final String authority;
}
