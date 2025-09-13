package com.homespot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    @PostMapping
    public ResponseEntity<?> addFavorite(@RequestBody Map<String, Integer> payload) {
        // Stubbed success; integrate with auth and Favorite repo later
        return ResponseEntity.ok(Map.of("status", "ok"));
    }
}