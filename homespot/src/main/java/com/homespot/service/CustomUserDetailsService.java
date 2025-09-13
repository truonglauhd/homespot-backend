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
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<UserRole> roles = userRoleRepository.findByUser(user);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (UserRole ur : roles) {
            Role r = ur.getRole();
            if (r != null && r.getName() != null) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + r.getName().toUpperCase()));
            }
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), authorities);
    }
}
