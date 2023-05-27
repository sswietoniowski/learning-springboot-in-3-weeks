package com.example.demospring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // some of this is deprecated, but it's just for demo
        return http
                .authorizeHttpRequests()
                .requestMatchers("/index.html", "/api/skills").permitAll()
                .requestMatchers("/api/employees/**").authenticated()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .and()
                .build();
    }
}
