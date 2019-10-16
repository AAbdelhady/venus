package com.venus.feature.common.dto;

import com.venus.feature.common.enums.AuthProvider;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SocialUserDetails {
    private String loginId;
    private String firstName;
    private String lastName;
    private String email;
    private AuthProvider authProvider;
    private String profilePictureUrl;
}
