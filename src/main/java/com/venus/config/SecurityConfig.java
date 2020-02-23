package com.venus.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

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
    private final AuthenticationFailureHandler socialAuthenticationFailureHandler;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final LogoutHandler logoutHandler;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().mvcMatchers("/swagger-ui.html/**", "/configuration/**", "/swagger-resources/**", "/v2/api-docs", "/webjars/**", "/image/**");
    }

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
                .antMatchers("/test/secure").authenticated()
                .antMatchers("/test", "/test/**").permitAll()
                .antMatchers(HttpMethod.GET, "/artist/category").permitAll()
                .antMatchers("/**").authenticated()
                .and()
                .oauth2Login()
                .successHandler(socialAuthenticationSuccessHandler)
                .failureHandler(socialAuthenticationFailureHandler)
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
                .addFilterBefore(tokenAuthenticationFilter, BasicAuthenticationFilter.class);
        // .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }
}
