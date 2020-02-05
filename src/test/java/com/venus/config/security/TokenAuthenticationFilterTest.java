package com.venus.config.security;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import com.venus.config.security.utils.CookieUtil;
import com.venus.feature.user.entity.User;
import com.venus.feature.user.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

import static com.venus.testutils.AssertionUtils.assertUserInSecurityContext;
import static com.venus.testutils.UnitTestUtils.createDummyUser;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TokenAuthenticationFilterTest {

    private final static String AUTH_HEADER_NAME = (String) ReflectionTestUtils.getField(TokenAuthenticationFilter.class, "AUTH_HEADER_NAME");
    private final static String TOKEN_PREFIX = (String) ReflectionTestUtils.getField(TokenAuthenticationFilter.class, "TOKEN_PREFIX");

    private TokenAuthenticationFilter tokenAuthenticationFilter;

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        tokenAuthenticationFilter = new TokenAuthenticationFilter(tokenProvider, userRepository);
        SecurityContextHolder.clearContext();
    }

    @Test
    public void doFilterInternal_shouldExtractTokenFromCookieFirst() throws ServletException, IOException {
        // given
        final User user = createDummyUser();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        final String tokenValue = "12345";
        Cookie cookie = new Cookie(CookieUtil.JWT_COOKIE, tokenValue);
        request.setCookies(cookie);

        when(tokenProvider.getUserIdFromToken(tokenValue)).thenReturn(user.getId());
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // when
        tokenAuthenticationFilter.doFilterInternal(request, response, new MockFilterChain());

        // then
        assertUserInSecurityContext(user);
    }

    @Test
    public void doFilterInternal_shouldExtractTokenFromHeaderIfNotInCookie() throws ServletException, IOException {
        // given
        final User user = createDummyUser();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        final String tokenValue = "12345";
        request.addHeader(AUTH_HEADER_NAME, TOKEN_PREFIX + tokenValue);

        when(tokenProvider.getUserIdFromToken(tokenValue)).thenReturn(user.getId());
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // when
        tokenAuthenticationFilter.doFilterInternal(request, response, new MockFilterChain());

        // then
        assertUserInSecurityContext(user);
    }

    @Test
    public void doFilterInternal_shouldNotSetContext_whenNoTokenFoundInCookiesOrHeaders() throws ServletException, IOException {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // when
        tokenAuthenticationFilter.doFilterInternal(request, response, new MockFilterChain());

        // then
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void doFilterInternal_shouldNotSetContext_whenJwtExpired() throws ServletException, IOException {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        final String tokenValue = "12345";
        Cookie cookie = new Cookie(CookieUtil.JWT_COOKIE, tokenValue);
        request.setCookies(cookie);

        Claims claims = mock(Claims.class);
        when(claims.getSubject()).thenReturn("-1");
        when(claims.getExpiration()).thenReturn(new Date());
        ExpiredJwtException exception = new ExpiredJwtException(null, claims, null);
        when(tokenProvider.getUserIdFromToken(tokenValue)).thenThrow(exception);

        // when
        tokenAuthenticationFilter.doFilterInternal(request, response, new MockFilterChain());

        // then
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void doFilterInternal_shouldNotSetContext_whenUserInClaimsNotExist() throws ServletException, IOException {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        final String tokenValue = "12345";
        Cookie cookie = new Cookie(CookieUtil.JWT_COOKIE, tokenValue);
        request.setCookies(cookie);

        when(tokenProvider.getUserIdFromToken(tokenValue)).thenReturn(-1L);
        when(userRepository.findById(-1L)).thenReturn(Optional.empty());

        // when
        tokenAuthenticationFilter.doFilterInternal(request, response, new MockFilterChain());

        // then
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void doFilterInternal_shouldNotSetContext_whenUnsupportedJwtExceptionThrown() throws ServletException, IOException {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        final String tokenValue = "12345";
        Cookie cookie = new Cookie(CookieUtil.JWT_COOKIE, tokenValue);
        request.setCookies(cookie);

        when(tokenProvider.getUserIdFromToken(tokenValue)).thenThrow(new UnsupportedJwtException(""));

        // when
        tokenAuthenticationFilter.doFilterInternal(request, response, new MockFilterChain());

        // then
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void doFilterInternal_shouldNotSetContext_whenIllegalArgumentExceptionThrown() throws ServletException, IOException {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        final String tokenValue = "12345";
        Cookie cookie = new Cookie(CookieUtil.JWT_COOKIE, tokenValue);
        request.setCookies(cookie);

        when(tokenProvider.getUserIdFromToken(tokenValue)).thenThrow(new IllegalArgumentException());

        // when
        tokenAuthenticationFilter.doFilterInternal(request, response, new MockFilterChain());

        // then
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}