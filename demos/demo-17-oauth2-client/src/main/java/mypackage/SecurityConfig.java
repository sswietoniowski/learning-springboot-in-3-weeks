package mypackage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests()
                    .requestMatchers("/index.html", "/controller1").permitAll()
                    .requestMatchers("/controller2").authenticated()
                    .anyRequest().authenticated()
                .and()
                    .oauth2Login()
                .and()
                    .build();
    }
}
