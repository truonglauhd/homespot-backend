package com.homespot.mapper;

import com.homespot.dto.UserDTO;
import com.homespot.model.Role;
import com.homespot.model.User;

import java.util.stream.Collectors;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setRoles(
                user.getUserRoles().stream()
                        .map(userRole -> userRole.getRole().getName())
                        .collect(Collectors.toSet())
        );
        return dto;
    }
}
