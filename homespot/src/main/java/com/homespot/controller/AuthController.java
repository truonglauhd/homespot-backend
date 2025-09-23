package com.homespot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import com.homespot.security.JwtUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("=== LOGIN CONTROLLER CALLED ===");
        System.out.println("Request received: " + loginRequest);
        System.out.println("Email: " + loginRequest.getEmail());
        System.out.println("Password provided: " + (loginRequest.getPassword() != null && !loginRequest.getPassword().isEmpty()));
        
        try {
            System.out.println("Attempting authentication...");
            
            UsernamePasswordAuthenticationToken authToken = 
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(), 
                    loginRequest.getPassword()
                );
            
            System.out.println("Authentication token created, calling authenticate...");
            
            Authentication authentication = authenticationManager.authenticate(authToken);
            
            System.out.println("Authentication successful!");
            System.out.println("Authenticated user: " + authentication.getName());
            System.out.println("Authorities: " + authentication.getAuthorities());
            
            List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> auth.startsWith("ROLE_"))
                .map(auth -> {
                    String roleName = auth.substring(5);
                    return roleName.substring(0, 1).toUpperCase() + roleName.substring(1).toLowerCase();
                })
                .collect(Collectors.toList());
            
            System.out.println("Extracted roles for JWT: " + roles);
            
            Map<String, Object> claims = new HashMap<>();
            claims.put("email", authentication.getName());
            claims.put("roles", roles); 
            
            String jwt = jwtUtil.generateToken(authentication.getName(), claims);
            System.out.println("JWT generated with roles: " + jwt.substring(0, 20) + "...");
            
            return ResponseEntity.ok(new LoginResponse(jwt, authentication.getName()));
            
        } catch (BadCredentialsException e) {
            System.out.println("Authentication failed: Bad credentials");
            return ResponseEntity.status(401).body(new ErrorResponse("Invalid email or password"));
            
        } catch (Exception e) {
            System.out.println("Authentication failed with exception: " + e.getClass().getName());
            System.out.println("Exception message: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ErrorResponse("Authentication failed: " + e.getMessage()));
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        System.out.println("Auth test endpoint called");
        return ResponseEntity.ok("Auth controller is working!");
    }

    public static class LoginRequest {
        private String email;
        private String password;
        
        public LoginRequest() {}
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        
        @Override
        public String toString() {
            return "LoginRequest{email='" + email + "', password=" + (password != null ? "[PROVIDED]" : "[NULL]") + "}";
        }
    }

    public static class LoginResponse {
        private String token;
        private String email;
        
        public LoginResponse(String token, String email) {
            this.token = token;
            this.email = email;
        }
        
        public String getToken() { return token; }
        public String getEmail() { return email; }
    }

    public static class ErrorResponse {
        private String message;
        
        public ErrorResponse(String message) {
            this.message = message;
        }
        
        public String getMessage() { return message; }
    }
}