package com.store.store.security.config;

import com.store.store.authorization.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class ApiSecurityConfig {

    private JWTRequestFilter jwtRequestFilter;

    public ApiSecurityConfig(JWTRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.addFilterBefore(jwtRequestFilter, AuthorizationFilter.class);
        httpSecurity.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/authenticate").anonymous()
                        .requestMatchers("/swagger-ui/*").permitAll()
                        .requestMatchers("/api-docs", "/api-docs/*").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }
}
