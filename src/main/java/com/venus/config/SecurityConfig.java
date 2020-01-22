package com.venus.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.venus.config.security.TokenAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${frontend.base-url}")
    private String frontendBaseUrl;

    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AuthenticationSuccessHandler socialAuthenticationSuccessHandler;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final LogoutHandler logoutHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**")
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/test/secure").authenticated()
                .antMatchers("/api/test").permitAll()
                .antMatchers("/api/test/redirect").permitAll()
                .antMatchers("/api/**").authenticated()
                .and()
                .oauth2Login()
                .successHandler(socialAuthenticationSuccessHandler)
                .and()
                .logout()
                .addLogoutHandler(logoutHandler)
                .logoutSuccessUrl(frontendBaseUrl)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .csrf()
                .disable()
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        // .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }
}
