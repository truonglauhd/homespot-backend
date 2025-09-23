package com.homespot.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import com.homespot.service.CustomUserDetailsService;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        // Skip JWT processing for auth endpoints, OPTIONS requests, and error pages
        if (requestURI.startsWith("/api/auth/") ||
                "OPTIONS".equals(method) ||
                requestURI.startsWith("/error")) {
            System.out.println("=== SKIPPING JWT FILTER ===");
            System.out.println("Method: " + method);
            System.out.println("URI: " + requestURI);
            System.out.println("Headers: " + java.util.Collections.list(request.getHeaderNames()));
            System.out.println("==============================");
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // Log requests to /api/estates for debugging
        if (request.getRequestURI().startsWith("/api/estates")) {
            System.out.println("=== ESTATE REQUEST ===");
            System.out.println("Request URI: " + request.getRequestURI());
            System.out.println("Request Method: " + request.getMethod());
            System.out.println("Authorization header: " + header);
            System.out.println("=====================================");
        }

        try {
            if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
                token = header.substring(7);
                // Extract username/email from token
                username = jwtUtil.getSubject(token);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Validate token (your method only takes String parameter)
                boolean isValid = jwtUtil.validateToken(token);

                if (isValid) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Add debug logging here
                    System.out.println("=== JWT FILTER DEBUG ===");
                    System.out.println("Setting authentication for user: " + userDetails.getUsername());
                    System.out.println("User authorities: " + userDetails.getAuthorities());
                    System.out.println("========================");

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        } catch (Exception e) {
            System.out.println("JWT Authentication failed: " + e.getMessage());
            // Don't block the request, just continue without authentication
        }

        filterChain.doFilter(request, response);
    }
}