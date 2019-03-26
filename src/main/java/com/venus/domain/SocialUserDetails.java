package com.venus.domain;

import com.venus.domain.enums.AuthProvider;

import lombok.Data;

@Data
public class SocialUserDetails {
    private String loginId;
    private String firstName;
    private String lastName;
    private String email;
    private AuthProvider authProvider;
    private String profilePictureUrl;
}
