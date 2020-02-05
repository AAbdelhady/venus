package com.venus.config.security;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.assertEquals;

public class CustomAuthenticationEntryPointTest {

    private CustomAuthenticationEntryPoint authenticationEntryPoint = new CustomAuthenticationEntryPoint();

    @Test
    public void commence_shouldSetStatusTo401() {
        // given
        HttpServletResponse response = new MockHttpServletResponse();

        // when
        authenticationEntryPoint.commence(null, response, null);

        // then
        assertEquals(401, response.getStatus());
    }
}