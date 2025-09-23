package com.homespot.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/test")
public class TestController {
    
    public TestController() {
        System.out.println("=== TestController CREATED ===");
    }
    
    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        System.out.println("=== TEST HELLO ENDPOINT CALLED ===");
        return ResponseEntity.ok("Hello from TestController!");
    }
}