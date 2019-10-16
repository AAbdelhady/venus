package com.venus.config.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.util.ReflectionTestUtils;

import com.venus.config.security.utils.SecurityUtil;
import com.venus.feature.common.enums.Role;
import com.venus.feature.user.entity.User;
import com.venus.feature.user.repository.UserRepository;

import static com.venus.feature.common.enums.AuthProvider.facebook;
import static com.venus.feature.common.enums.AuthProvider.google;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtil.class)
public class SocialAuthenticationSuccessHandlerTest {

    private SocialAuthenticationSuccessHandler successHandler;

    @Mock
    UserRepository userRepository;

    @Mock
    TokenProvider tokenProvider;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        initMocks();
        successHandler = new SocialAuthenticationSuccessHandler(userRepository, tokenProvider);
        ReflectionTestUtils.setField(successHandler, "frontendBaseUrl", "http://frontend.com");
    }

    @Test
    public void onAuthenticationSuccessTest_facebook_newUser() throws IOException {
        /* given */
        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();

        Authentication authentication = new OAuth2AuthenticationToken(dummyFacebookOauth2User(), AuthorityUtils.createAuthorityList(Role.USER.getAuthority()), "facebook");

        /* when */
        successHandler.onAuthenticationSuccess(request, response, authentication);

        /*then */
        assertEquals(302, response.getStatus());
        assertEquals("http://frontend.com/redirect", response.getHeader("Location"));
        assertEquals("random-token", response.getHeader("JWT"));
        assertEquals("86400", response.getHeader("JWT_TTL_SEC"));
        assertEquals("random-token", ((MockHttpServletResponse) response).getCookie("JWT").getValue());
        assertEquals(86400, ((MockHttpServletResponse) response).getCookie("JWT").getMaxAge());
        assertTrue(((MockHttpServletResponse) response).getCookie("JWT").isHttpOnly());

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(captor.capture());

        User user = captor.getValue();
        assertEquals(facebook, user.getAuthProvider());
        assertEquals("facebook-id", user.getLoginId());
        assertEquals("facebook-first", user.getFirstName());
        assertEquals("facebook-last", user.getLastName());
        assertEquals("facebook-email", user.getEmail());
    }

    @Test
    public void onAuthenticationSuccessTest_facebook_existingUser() throws IOException {
        /* given */
        User existingUser = User.builder().loginId("facebook-id").firstName("old-first").lastName("old-last").email("old-email").build();
        when(userRepository.findOneByLoginId("facebook-id")).thenReturn(Optional.of(existingUser));

        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();

        Authentication authentication = new OAuth2AuthenticationToken(dummyFacebookOauth2User(), AuthorityUtils.createAuthorityList(Role.USER.getAuthority()), "facebook");

        /* when */
        successHandler.onAuthenticationSuccess(request, response, authentication);

        /*then */
        assertEquals(302, response.getStatus());
        assertEquals("http://frontend.com/redirect", response.getHeader("Location"));
        assertEquals("random-token", response.getHeader("JWT"));
        assertEquals("86400", response.getHeader("JWT_TTL_SEC"));
        assertEquals("random-token", ((MockHttpServletResponse) response).getCookie("JWT").getValue());
        assertEquals(86400, ((MockHttpServletResponse) response).getCookie("JWT").getMaxAge());
        assertTrue(((MockHttpServletResponse) response).getCookie("JWT").isHttpOnly());

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(captor.capture());

        User user = captor.getValue();
        assertEquals("facebook-id", user.getLoginId());
        assertEquals("facebook-first", user.getFirstName());
        assertEquals("facebook-last", user.getLastName());
        assertEquals("facebook-email", user.getEmail());
    }

    @Test
    public void onAuthenticationSuccessTest_google_newUser() throws IOException {
        /* given */
        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();

        Authentication authentication = new OAuth2AuthenticationToken(dummyGoogleOauth2User(), AuthorityUtils.createAuthorityList(Role.USER.getAuthority()), "google");

        /* when */
        successHandler.onAuthenticationSuccess(request, response, authentication);

        /*then */
        assertEquals(302, response.getStatus());
        assertEquals("http://frontend.com/redirect", response.getHeader("Location"));
        assertEquals("random-token", response.getHeader("JWT"));
        assertEquals("86400", response.getHeader("JWT_TTL_SEC"));
        assertEquals("random-token", ((MockHttpServletResponse) response).getCookie("JWT").getValue());
        assertEquals(86400, ((MockHttpServletResponse) response).getCookie("JWT").getMaxAge());
        assertTrue(((MockHttpServletResponse) response).getCookie("JWT").isHttpOnly());

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(captor.capture());

        User user = captor.getValue();
        assertEquals(google, user.getAuthProvider());
        assertEquals("google-id", user.getLoginId());
        assertEquals("google-first", user.getFirstName());
        assertEquals("google-last", user.getLastName());
        assertEquals("google-email", user.getEmail());
    }

    @Test
    public void onAuthenticationSuccessTest_google_existingUser() throws IOException {
        /* given */
        User existingUser = User.builder().loginId("facebook-id").firstName("old-first").lastName("old-last").email("old-email").build();
        when(userRepository.findOneByLoginId("facebook-id")).thenReturn(Optional.of(existingUser));

        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();

        Authentication authentication = new OAuth2AuthenticationToken(dummyGoogleOauth2User(), AuthorityUtils.createAuthorityList(Role.USER.getAuthority()), "google");

        /* when */
        successHandler.onAuthenticationSuccess(request, response, authentication);

        /*then */
        assertEquals(302, response.getStatus());
        assertEquals("http://frontend.com/redirect", response.getHeader("Location"));
        assertEquals("random-token", response.getHeader("JWT"));
        assertEquals("86400", response.getHeader("JWT_TTL_SEC"));
        assertEquals("random-token", ((MockHttpServletResponse) response).getCookie("JWT").getValue());
        assertEquals(86400, ((MockHttpServletResponse) response).getCookie("JWT").getMaxAge());
        assertTrue(((MockHttpServletResponse) response).getCookie("JWT").isHttpOnly());

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(captor.capture());

        User user = captor.getValue();
        assertEquals("google-id", user.getLoginId());
        assertEquals("google-first", user.getFirstName());
        assertEquals("google-last", user.getLastName());
        assertEquals("google-email", user.getEmail());
    }

    private void initMocks() throws Exception {
        PowerMockito.spy(SecurityUtil.class);
        PowerMockito.doNothing().when(SecurityUtil.class, "updateCurrentUserContext", any());
        when(userRepository.save(isA(User.class))).then(saveUserAnswer());
        when(tokenProvider.createToken(isA(User.class))).thenReturn("random-token");
    }

    private OAuth2User dummyFacebookOauth2User() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("id", "facebook-id");
        attributes.put("first_name", "facebook-first");
        attributes.put("last_name", "facebook-last");
        attributes.put("email", "facebook-email");

        return new DefaultOAuth2User(AuthorityUtils.createAuthorityList(Role.USER.getAuthority()), attributes, "id");
    }

    private OAuth2User dummyGoogleOauth2User() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", "google-id");
        attributes.put("given_name", "google-first");
        attributes.put("family_name", "google-last");
        attributes.put("email", "google-email");

        return new DefaultOAuth2User(AuthorityUtils.createAuthorityList(Role.USER.getAuthority()), attributes, "sub");
    }

    private Answer<User> saveUserAnswer() {
        return invocation -> (User) invocation.getArguments()[0];
    }
}