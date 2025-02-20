package com.kachinga.eschool.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/auth/**", "/ws/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/grade-levels", "/api/v1/grade-levels/**").hasRole("ESCHOOL_SUPER_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/grade-levels", "/api/v1/grade-levels/**").hasRole("ESCHOOL_SUPER_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/grade-levels", "/api/v1/grade-levels/**").hasRole("ESCHOOL_SUPER_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/subjects", "/api/v1/subjects/**").hasRole("ESCHOOL_SUPER_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/subjects", "/api/v1/subjects/**").hasRole("ESCHOOL_SUPER_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/subjects", "/api/v1/subjects/**").hasRole("ESCHOOL_SUPER_ADMIN")
                        .anyRequest().authenticated())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
