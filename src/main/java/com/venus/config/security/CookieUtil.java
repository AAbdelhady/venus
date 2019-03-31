package com.venus.config.security;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

class CookieUtil {

    static final String JWT_COOKIE = "jwt";

    static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
}
