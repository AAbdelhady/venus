package com.venus.config.security;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.venus.domain.SocialUserDetails;
import com.venus.domain.entities.user.User;
import com.venus.domain.enums.AuthProvider;
import com.venus.domain.enums.Role;
import com.venus.repositories.UserRepository;
import com.venus.services.user.UserService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SocialAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    private final UserRepository userRepository;

    @Autowired
    public SocialAuthenticationSuccessHandler(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        SocialUserDetails details = getSocialUserDetails(authentication);

        Optional<User> userOptional = userRepository.findOneByLoginId(details.getLoginId());

        User loggedInUser;
        if (userOptional.isPresent())
            loggedInUser = updateExistingSocialLoginUser(userOptional.get(), details);
        else
            loggedInUser = registerNewSocialLoginUser(details);
        UserAuthenticationUtil.updateCurrentUserContext(loggedInUser);

        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
    }

    private SocialUserDetails getSocialUserDetails(Authentication authentication) {
        Map<String, Object> socialDetailsMap = ((OAuth2User) authentication.getPrincipal()).getAttributes();
        String socialLoginProviderName = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
        switch (AuthProvider.valueOf(socialLoginProviderName)) {
            case FACEBOOK:
                return addDataFromFacebook(socialDetailsMap);
            case GOOGLE:
                return addDataFromGoogle(socialDetailsMap);
        }
        throw new IllegalArgumentException();
    }

    private User updateExistingSocialLoginUser(User user, SocialUserDetails details) {
        user.setEmail(details.getEmail());
        user.setFirstName(details.getFirstName());
        user.setLastName(details.getLastName());
        user.setProfilePictureUrl(details.getProfilePictureUrl());
        return userRepository.save(user);
    }

    private User registerNewSocialLoginUser(SocialUserDetails details) {
        User user = User.builder()
                .authProvider(details.getAuthProvider())
                .loginId(details.getLoginId())
                .role(Role.USER)
                .firstName(details.getFirstName())
                .lastName(details.getLastName())
                .email(details.getEmail())
                .profilePictureUrl(details.getProfilePictureUrl())
                .build();
        return userService.saveUser(user);
    }

    private SocialUserDetails addDataFromFacebook(Map<String, Object> socialDetailsMap) {
        SocialUserDetails details = new SocialUserDetails();
        details.setAuthProvider(AuthProvider.FACEBOOK);
        details.setLoginId((String) socialDetailsMap.get("id"));
        details.setFirstName((String) socialDetailsMap.get("first_name"));
        details.setLastName((String) socialDetailsMap.get("last_name"));
        details.setEmail((String) socialDetailsMap.get("email"));
        details.setProfilePictureUrl((String) (((LinkedHashMap) ((LinkedHashMap) socialDetailsMap.get("picture")).get("data"))).get("url"));
        return details;
    }

    private SocialUserDetails addDataFromGoogle(Map<String, Object> socialDetailsMap) {
        SocialUserDetails details = new SocialUserDetails();
        details.setAuthProvider(AuthProvider.GOOGLE);
        details.setLoginId((String) socialDetailsMap.get("sub"));
        details.setFirstName((String) socialDetailsMap.get("given_name"));
        details.setLastName((String) socialDetailsMap.get("family_name"));
        details.setEmail((String) socialDetailsMap.get("email"));
        details.setProfilePictureUrl((String) socialDetailsMap.get("picture"));
        return details;
    }
}
