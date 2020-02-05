package com.venus.config.security;

import javax.servlet.http.Cookie;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import com.venus.config.security.utils.CookieUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class CustomLogoutHandlerTest {

    private CustomLogoutHandler logoutHandler = new CustomLogoutHandler();

    @Test
    public void logout_shouldInvalidateCookie() {
        // given
        MockHttpServletResponse response = new MockHttpServletResponse();

        // when
        logoutHandler.logout(null, response, null);

        // then
        Cookie cookie = response.getCookie(CookieUtil.JWT_COOKIE);
        assertNotNull(cookie);
        assertEquals("/", cookie.getPath());
        assertNull(cookie.getValue());
        assertEquals(0, cookie.getMaxAge());
        assertTrue(cookie.isHttpOnly());
        assertFalse(cookie.getSecure());
    }
}