package com.homespot.config;

import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.http.SessionCreationPolicy;
import com.homespot.service.CustomUserDetailsService;
import com.homespot.security.JwtUtil;
import com.homespot.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.http.HttpStatus;

@Configuration
public class SecurityConfig {
    @Autowired private JwtUtil jwtUtil;
    @Autowired private CustomUserDetailsService userDetailsService;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtUtil, userDetailsService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("=== CONFIGURING SECURITY FILTER CHAIN ===");
        
        http
                .cors(cors -> cors.configurationSource(request -> {
                    System.out.println("CORS request from origin: " + request.getHeader("Origin"));
                    var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                    corsConfig.setAllowedOrigins(java.util.List.of(
                        "http://localhost:3000", 
                        "http://localhost:3001", 
                        "http://127.0.0.1:3000",
                        "http://127.0.0.1:3001"
                    ));
                    corsConfig.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
                    corsConfig.setAllowedHeaders(java.util.List.of("*"));
                    corsConfig.setExposedHeaders(java.util.List.of("Authorization"));
                    corsConfig.setAllowCredentials(true);
                    corsConfig.setMaxAge(3600L);
                    return corsConfig;
                }))
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(eh -> eh.authenticationEntryPoint((request, response, authException) -> {
                    System.out.println("=== AUTHENTICATION ENTRY POINT TRIGGERED ===");
                    System.out.println("Request: " + request.getMethod() + " " + request.getRequestURI());
                    System.out.println("Auth Exception: " + authException.getMessage());
                    System.out.println("============================================");
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().write("{\"error\":\"Unauthorized\"}");
                }))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        
                        .requestMatchers("/api/auth/**").permitAll()
                        
                        .requestMatchers("/error").permitAll()
                        
                        .requestMatchers("/api/agent/test").permitAll()
                        
                        .requestMatchers(HttpMethod.GET, "/api/properties/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/estates/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/agents/**").permitAll()
                        
                        .requestMatchers(HttpMethod.POST, "/api/estates/test").permitAll()
                        
                        .requestMatchers(HttpMethod.GET, "/api/estates/my-estates").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/estates").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/estates/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/estates/**").authenticated()
                        
                        .requestMatchers("/api/agent/**").authenticated()
                        
                        .requestMatchers("/api/favorites/**").authenticated()
                        
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        System.out.println("=== SECURITY FILTER CHAIN CONFIGURED ===");
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}