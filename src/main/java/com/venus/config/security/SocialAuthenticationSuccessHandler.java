package com.venus.config.security;

import java.io.IOException;
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

import lombok.extern.slf4j.Slf4j;

import static com.venus.config.security.SecurityUtil.JWT_HEADER_NAME;
import static com.venus.config.security.SecurityUtil.JWT_TTL_HEADER_NAME;
import static com.venus.config.security.SecurityUtil.JWT_TTL_SECONDS;

@Component
@Slf4j
public class SocialAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    private final TokenProvider tokenProvider;

    @Autowired
    public SocialAuthenticationSuccessHandler(UserRepository userRepository, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        SocialUserDetails details = getSocialUserDetails(authentication);

        Optional<User> userOptional = userRepository.findOneByLoginId(details.getLoginId());

        User loggedInUser;
        if (userOptional.isPresent())
            loggedInUser = updateExistingSocialLoginUser(userOptional.get(), details);
        else
            loggedInUser = registerNewSocialLoginUser(details);
        UserAuthenticationUtil.updateCurrentUserContext(loggedInUser);

        String token = tokenProvider.createToken(loggedInUser);

        CookieUtil.addCookie(response, CookieUtil.JWT_COOKIE, token, JWT_TTL_SECONDS); // for webapps
        response.setHeader(JWT_HEADER_NAME, token); // for mobile clients
        response.setHeader(JWT_TTL_HEADER_NAME, String.valueOf(JWT_TTL_SECONDS));
        response.setStatus(HttpServletResponse.SC_OK);

        /*RedirectStrategy strategy = new DefaultRedirectStrategy();
        strategy.sendRedirect(request, response, "http://localhost:8080?token="+token+"&id="+loggedInUser.getId());*/
    }

    private SocialUserDetails getSocialUserDetails(Authentication authentication) {
        Map<String, Object> socialDetailsMap = ((OAuth2User) authentication.getPrincipal()).getAttributes();
        String socialLoginProviderName = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
        switch (AuthProvider.valueOf(socialLoginProviderName)) {
            case facebook:
                return addDataFromFacebook(socialDetailsMap);
            case google:
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
        return userRepository.save(user);
    }

    private SocialUserDetails addDataFromFacebook(Map<String, Object> socialDetailsMap) {
        SocialUserDetails details = new SocialUserDetails();
        details.setAuthProvider(AuthProvider.facebook);
        details.setLoginId((String) socialDetailsMap.get("id"));
        details.setFirstName((String) socialDetailsMap.get("first_name"));
        details.setLastName((String) socialDetailsMap.get("last_name"));
        details.setEmail((String) socialDetailsMap.get("email"));
        details.setProfilePictureUrl((String) (((LinkedHashMap) ((LinkedHashMap) socialDetailsMap.get("picture")).get("data"))).get("url"));
        return details;
    }

    private SocialUserDetails addDataFromGoogle(Map<String, Object> socialDetailsMap) {
        SocialUserDetails details = new SocialUserDetails();
        details.setAuthProvider(AuthProvider.google);
        details.setLoginId((String) socialDetailsMap.get("sub"));
        details.setFirstName((String) socialDetailsMap.get("given_name"));
        details.setLastName((String) socialDetailsMap.get("family_name"));
        details.setEmail((String) socialDetailsMap.get("email"));
        details.setProfilePictureUrl((String) socialDetailsMap.get("picture"));
        return details;
    }
}
