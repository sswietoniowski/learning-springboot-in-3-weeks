package onlineretailer;

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
                    .requestMatchers("/admin.html").authenticated()
                    .requestMatchers("/**").permitAll()
                    .anyRequest().anonymous()
                .and()
                    .csrf().disable().cors()
                .and()
                    .oauth2Login()
                .and()
                    .build();
    }
}
