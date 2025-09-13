package com.homespot.service;

import com.homespot.dto.SignupRequest;
import com.homespot.dto.LoginRequest;
import com.homespot.model.User;
import com.homespot.model.UserRole;
import com.homespot.model.UserRoleId;
import com.homespot.model.Role;
import com.homespot.repository.UserRepository;
import com.homespot.repository.RoleRepository;
import com.homespot.repository.UserRoleRepository;
import com.homespot.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AuthService {
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private UserRoleRepository userRoleRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;

    public void signup(SignupRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);

        Role defaultRole = roleRepository.findByName("user")
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        UserRole ur = new UserRole();
        ur.setId(new UserRoleId(user.getId(), defaultRole.getId()));
        ur.setUser(user);
        ur.setRole(defaultRole);
        userRoleRepository.save(ur);
    }

    public String login(LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());

        return jwtUtil.generateToken(user.getEmail(), claims);
    }
}
