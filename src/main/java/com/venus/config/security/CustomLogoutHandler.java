package com.venus.config.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import com.venus.config.security.utils.CookieUtil;

@Component
public class CustomLogoutHandler implements LogoutHandler {

    @Value("${server.secured:false}")
    private boolean isHttps;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        CookieUtil.addCookie(response, CookieUtil.JWT_COOKIE, null, 0, true, isHttps);
    }
}
