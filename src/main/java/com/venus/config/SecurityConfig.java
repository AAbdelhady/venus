package com.venus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.venus.config.security.SocialAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthenticationEntryPoint authenticationEntryPoint;

    private LogoutSuccessHandler logoutSuccessHandler;

    private AuthenticationSuccessHandler socialAuthenticationSuccessHandler;

    @Autowired
    public SecurityConfig(AuthenticationEntryPoint authenticationEntryPoint, LogoutSuccessHandler logoutSuccessHandler,
                          SocialAuthenticationSuccessHandler socialAuthenticationSuccessHandler) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.socialAuthenticationSuccessHandler = socialAuthenticationSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**")
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/api/test").permitAll()
                .antMatchers("/api/**").authenticated()
                .and()
                .oauth2Login()
                .successHandler(socialAuthenticationSuccessHandler)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .invalidateHttpSession(true)
                .and()
                .csrf()
                .disable();
        // .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }
}
