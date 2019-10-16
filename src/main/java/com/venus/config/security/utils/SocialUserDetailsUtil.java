package com.venus.config.security.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import com.venus.feature.common.dto.SocialUserDetails;
import com.venus.feature.common.enums.AuthProvider;

public class SocialUserDetailsUtil {

    public static SocialUserDetails parseFacebookUserDetails(Map<String, Object> socialDetailsMap) {
        return SocialUserDetails.builder()
                .authProvider(AuthProvider.facebook)
                .loginId((String) socialDetailsMap.get("id"))
                .firstName((String) socialDetailsMap.get("first_name"))
                .lastName((String) socialDetailsMap.get("last_name"))
                .email((String) socialDetailsMap.get("email"))
                .profilePictureUrl(getFacebookProfilePictureUrl(socialDetailsMap))
                .build();
    }

    public static SocialUserDetails parseGoogleUserDetails(Map<String, Object> socialDetailsMap) {
        return SocialUserDetails.builder()
                .authProvider(AuthProvider.google)
                .loginId((String) socialDetailsMap.get("sub"))
                .firstName((String) socialDetailsMap.get("given_name"))
                .lastName((String) socialDetailsMap.get("family_name"))
                .email((String) socialDetailsMap.get("email"))
                .profilePictureUrl((String) socialDetailsMap.get("picture"))
                .build();
    }

    private static String getFacebookProfilePictureUrl(Map<String, Object> socialDetailsMap) {
        LinkedHashMap picture = (LinkedHashMap) socialDetailsMap.get("picture");
        if (picture != null) {
            LinkedHashMap data = (LinkedHashMap) picture.get("data");
            if (data != null)
                return (String) data.get("url");
        }
        return null;
    }
}
