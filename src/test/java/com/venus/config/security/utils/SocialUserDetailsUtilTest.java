package com.venus.config.security.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import com.venus.feature.common.dto.SocialUserDetails;

import static org.junit.Assert.assertEquals;

public class SocialUserDetailsUtilTest {

    @Test
    public void parseFacebookUserDetails() {
        /* given */
        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("url", "facebook-profile-pic");
        LinkedHashMap<String, Object> picture = new LinkedHashMap<>();
        picture.put("data", data);

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("id", "facebook-id");
        attributes.put("first_name", "facebook-first");
        attributes.put("last_name", "facebook-last");
        attributes.put("email", "facebook-email");
        attributes.put("picture", picture);

        /* when */
        SocialUserDetails socialUserDetails = SocialUserDetailsUtil.parseFacebookUserDetails(attributes);

        /* then */
        assertEquals("facebook-id", socialUserDetails.getLoginId());
        assertEquals("facebook-first", socialUserDetails.getFirstName());
        assertEquals("facebook-last", socialUserDetails.getLastName());
        assertEquals("facebook-email", socialUserDetails.getEmail());
        assertEquals("facebook-profile-pic", socialUserDetails.getProfilePictureUrl());
    }

    @Test
    public void parseGoogleUserDetails() {
        /* given */
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", "google-id");
        attributes.put("given_name", "google-first");
        attributes.put("family_name", "google-last");
        attributes.put("email", "google-email");
        attributes.put("picture", "google-profile-pic");

        /* when */
        SocialUserDetails socialUserDetails = SocialUserDetailsUtil.parseGoogleUserDetails(attributes);

        /* then */
        assertEquals("google-id", socialUserDetails.getLoginId());
        assertEquals("google-first", socialUserDetails.getFirstName());
        assertEquals("google-last", socialUserDetails.getLastName());
        assertEquals("google-email", socialUserDetails.getEmail());
        assertEquals("google-profile-pic", socialUserDetails.getProfilePictureUrl());
    }
}