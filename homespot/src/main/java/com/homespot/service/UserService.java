package com.homespot.service;

import com.homespot.dto.UserDTO;
import com.homespot.mapper.UserMapper;
import com.homespot.model.User;
import com.homespot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired private UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Integer id) {
        return userRepository.findById(id)
                .map(UserMapper::toDTO)
                .orElse(null);
    }

    public UserDTO getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserMapper::toDTO)
                .orElse(null);
    }
}
