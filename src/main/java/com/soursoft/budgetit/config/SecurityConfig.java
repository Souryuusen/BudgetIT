package com.soursoft.budgetit.config;

import com.soursoft.budgetit.services.CustomUserDetailsService;
import com.soursoft.budgetit.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private JwtUtil jwtUtil;

    private CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(CsrfConfigurer::disable)
            .authorizeHttpRequests((auth) -> {
                auth
                    .requestMatchers("/api/auth").permitAll()
                    .anyRequest().authenticated();
            })
            .sessionManagement((session) -> {
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            })

            .httpBasic(Customizer.withDefaults());
        return http.build();
    }


}
