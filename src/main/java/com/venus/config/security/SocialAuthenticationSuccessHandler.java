package com.venus.config.security;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.venus.config.security.utils.CookieUtil;
import com.venus.config.security.utils.SecurityUtil;
import com.venus.domain.SocialUserDetails;
import com.venus.domain.entities.user.User;
import com.venus.domain.enums.AuthProvider;
import com.venus.domain.enums.Role;
import com.venus.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

import static com.venus.config.security.utils.SecurityUtil.JWT_HEADER_NAME;
import static com.venus.config.security.utils.SecurityUtil.JWT_TTL_HEADER_NAME;
import static com.venus.config.security.utils.SecurityUtil.JWT_TTL_SECONDS;
import static com.venus.config.security.utils.SocialUserDetailsUtil.parseFacebookUserDetails;
import static com.venus.config.security.utils.SocialUserDetailsUtil.parseGoogleUserDetails;

@Component
@Slf4j
public class SocialAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final String REDIRECT_PAGE = "/redirect";

    private final UserRepository userRepository;

    private final TokenProvider tokenProvider;

    @Value("${frontend.base-url}")
    private String frontendBaseUrl;

    @Autowired
    public SocialAuthenticationSuccessHandler(UserRepository userRepository, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        SocialUserDetails details = getSocialUserDetails(authentication);
        Optional<User> userOptional = userRepository.findOneByLoginId(details.getLoginId());
        User loggedInUser = userOptional.isPresent() ? updateExistingSocialLoginUser(userOptional.get(), details) : registerNewSocialLoginUser(details);
        SecurityUtil.updateCurrentUserContext(loggedInUser);

        String token = tokenProvider.createToken(loggedInUser);
        CookieUtil.addCookie(response, CookieUtil.JWT_COOKIE, token, JWT_TTL_SECONDS); // for webapps
        response.setHeader(JWT_HEADER_NAME, token); // for mobile clients
        response.setHeader(JWT_TTL_HEADER_NAME, String.valueOf(JWT_TTL_SECONDS));

        RedirectStrategy strategy = new DefaultRedirectStrategy();
        strategy.sendRedirect(request, response, frontendBaseUrl + REDIRECT_PAGE);
    }

    private SocialUserDetails getSocialUserDetails(Authentication authentication) {
        Map<String, Object> socialDetailsMap = ((OAuth2User) authentication.getPrincipal()).getAttributes();
        String socialLoginProviderName = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
        switch (AuthProvider.valueOf(socialLoginProviderName)) {
            case facebook:
                return parseFacebookUserDetails(socialDetailsMap);
            case google:
                return parseGoogleUserDetails(socialDetailsMap);
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
}
