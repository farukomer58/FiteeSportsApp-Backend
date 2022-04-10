package com.fitee.config;

import com.fitee.exception.handlers.FilterExceptionHandler;
import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true) // enables @Secured(role)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    private final JwtFilter jwtFilter;
    private final CorsFilter corsFilter;
    private final FilterExceptionHandler filterExceptionHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .headers().frameOptions().disable() // allow h2-console
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(filterExceptionHandler, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(corsFilter, ChannelProcessingFilter.class)
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest().permitAll();
    }
}
