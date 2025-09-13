package com.homespot.controller;

import com.homespot.dto.UserDTO;
import com.homespot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired private UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @GetMapping("/by-email")
    public UserDTO getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }
}
