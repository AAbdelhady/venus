package com.venus.config.security.utils;

import java.util.Optional;

import javax.servlet.http.Cookie;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CookieUtilTest {

    @Test
    public void getCookie_shouldGetCookieFromRequest_ifExists() {
        // given
        final String cookieName = "cookie-name";
        Cookie cookie = new Cookie(cookieName, "cookieValue");

        MockHttpServletRequest requestA = new MockHttpServletRequest();
        requestA.setCookies(cookie);

        MockHttpServletRequest requestB = new MockHttpServletRequest();

        // when
        Optional<Cookie> resultA = CookieUtil.getCookie(requestA, cookieName);
        Optional<Cookie> resultB = CookieUtil.getCookie(requestB, cookieName);

        // then
        assertTrue(resultA.isPresent());
        assertEquals(cookie.getValue(), resultA.get().getValue());

        assertFalse(resultB.isPresent());
    }

    @Test
    public void addCookie_shouldAddCookieToResponse() {
        // given
        final String name = "cookie-name";
        final String value = "cookie-value";
        final int maxAge = 1234;

        MockHttpServletResponse response = new MockHttpServletResponse();
        assertEquals(0, response.getCookies().length);

        // given
        CookieUtil.addCookie(response, name, value, maxAge, true, true);

        // then
        Cookie cookie = response.getCookie(name);
        assertNotNull(cookie);
        assertEquals("/", cookie.getPath());
        assertEquals(value, cookie.getValue());
        assertEquals(maxAge, cookie.getMaxAge());
        assertTrue(cookie.getSecure());
        assertTrue(cookie.isHttpOnly());
    }
}