package com.homespot.service;

import com.homespot.model.User;
import com.homespot.model.UserRole;
import com.homespot.model.Role;
import com.homespot.repository.UserRepository;
import com.homespot.repository.UserRoleRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired private UserRepository userRepository;
    @Autowired private UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("=== LOADING USER BY USERNAME ===");
        System.out.println("Email: " + email);
        
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            System.out.println("User not found with email: " + email);
            throw new UsernameNotFoundException("User not found");
        }
        
        User user = userOptional.get();
        System.out.println("User found: " + user.getEmail());
        System.out.println("User password (first 10 chars): " + 
            (user.getPassword() != null ? user.getPassword().substring(0, Math.min(10, user.getPassword().length())) + "..." : "null"));

        List<UserRole> roles = userRoleRepository.findByUser(user);
        System.out.println("User roles count: " + roles.size());
        
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (UserRole ur : roles) {
            Role r = ur.getRole();
            if (r != null && r.getName() != null) {
                String authority = "ROLE_" + r.getName().toUpperCase();
                authorities.add(new SimpleGrantedAuthority(authority));
                System.out.println("Added authority: " + authority);
            }
        }

        // If no roles found, add default USER role
        if (authorities.isEmpty()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            System.out.println("No roles found, added default ROLE_USER");
        }

        System.out.println("Final authorities: " + authorities);
        System.out.println("================================");

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), authorities);
    }
}