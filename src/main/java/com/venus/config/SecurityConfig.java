package com.venus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.venus.config.security.SocialAuthenticationSuccessHandler;
import com.venus.config.security.TokenAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthenticationEntryPoint authenticationEntryPoint;

    private AuthenticationSuccessHandler socialAuthenticationSuccessHandler;

    private TokenAuthenticationFilter tokenAuthenticationFilter;

    @Autowired
    public SecurityConfig(AuthenticationEntryPoint authenticationEntryPoint, SocialAuthenticationSuccessHandler socialAuthenticationSuccessHandler,
                          TokenAuthenticationFilter tokenAuthenticationFilter) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.socialAuthenticationSuccessHandler = socialAuthenticationSuccessHandler;
        this.tokenAuthenticationFilter = tokenAuthenticationFilter;
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
                .antMatchers("/api/test/secure").authenticated()
                .antMatchers("/api/test").permitAll()
                .antMatchers("/api/test/redirect").permitAll()
                .antMatchers("/api/**").authenticated()
                .and()
                .oauth2Login()
                .successHandler(socialAuthenticationSuccessHandler)
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
