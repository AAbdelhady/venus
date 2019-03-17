package com.venus.config.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by asaid on 8/13/2017.
 */
@Component("logoutSuccessHandler")
@Slf4j
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        if (authentication != null) {
            log.info("Logout Successful for Principal: " + authentication.getName());
            httpServletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }
}
