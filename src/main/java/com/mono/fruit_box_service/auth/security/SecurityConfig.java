package com.mono.fruit_box_service.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // CSRF (keep enabled)
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/auth/**") //todo temporarly for testing, remove it after testing
                        .csrfTokenRepository(
                                CookieCsrfTokenRepository.withHttpOnlyFalse()
                        )
                )

                // 🔑 AUTH RULES (THIS IS THE IMPORTANT PART)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/mock/**").permitAll()
                        .requestMatchers(
                                "/auth/**",      // allow Google OAuth endpoints
                                "/error"         // allow error page
                        ).permitAll()
                        .requestMatchers("/users/**").authenticated()
                        .anyRequest().authenticated()
                )

                // Disable default form login
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())

                // JWT filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}



