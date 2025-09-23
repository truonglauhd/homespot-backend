// Updated UserService.java with proper role checking
package com.homespot.service;

import com.homespot.model.User;
import com.homespot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Collection;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
    
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            String email = ((UserDetails) authentication.getPrincipal()).getUsername();
            return findByEmail(email);
        }
        return null;
    }
    
    /**
     * Get current user roles from Spring Security context (without ROLE_ prefix)
     */
    public List<String> getCurrentUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("=== getCurrentUserRoles DEBUG ===");
        System.out.println("Authentication: " + authentication);
        if (authentication != null) {
            System.out.println("Authentication name: " + authentication.getName());
            System.out.println("Authentication principal: " + authentication.getPrincipal());
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            System.out.println("Raw authorities: " + authorities);
            
            List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> auth.startsWith("ROLE_"))
                .map(auth -> auth.substring(5)) // Remove "ROLE_" prefix
                .collect(Collectors.toList());
            
            System.out.println("Extracted roles: " + roles);
            System.out.println("===============================");
            return roles;
        }
        System.out.println("No authentication found");
        System.out.println("===============================");
        return List.of();
    }
    
    /**
     * Check if current user has specific role (case insensitive)
     */
    public boolean hasRole(String roleName) {
        List<String> userRoles = getCurrentUserRoles();
        return userRoles.stream()
            .anyMatch(role -> role.equalsIgnoreCase(roleName));
    }
    
    /**
     * Check if current user has any of the specified roles (case insensitive)
     */
    public boolean hasAnyRole(String... roleNames) {
        List<String> userRoles = getCurrentUserRoles();
        for (String roleName : roleNames) {
            if (userRoles.stream().anyMatch(role -> role.equalsIgnoreCase(roleName))) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Alternative method that works directly with Spring Security authorities
     * This is more direct and efficient
     */
    public boolean hasAuthority(String authority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals(authority));
        }
        return false;
    }
    
    /**
     * Check for role using Spring Security authority format (ROLE_ROLENAME)
     */
    public boolean hasSpringRole(String roleName) {
        return hasAuthority("ROLE_" + roleName.toUpperCase());
    }
}
