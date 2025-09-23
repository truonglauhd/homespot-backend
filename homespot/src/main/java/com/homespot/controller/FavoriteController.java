package com.homespot.controller;

import com.homespot.model.Favorite;
import com.homespot.model.FavoriteId;
import com.homespot.model.Post;
import com.homespot.model.User;
import com.homespot.repository.FavoriteRepository;
import com.homespot.repository.PostRepository;
import com.homespot.repository.UserRepository;
import com.homespot.dto.PostResponse;
import com.homespot.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> addFavorite(@RequestBody Map<String, Object> payload, Authentication auth) {
        try {
            if (auth == null || auth.getPrincipal() == null) {
                return ResponseEntity.status(401).body(Map.of("error", "Authentication required"));
            }

            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String email = userDetails.getUsername();

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Object postIdObj = payload.get("postId");
            if (postIdObj == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "postId is required"));
            }

            Integer postId;
            try {
                if (postIdObj instanceof String) {
                    postId = Integer.parseInt((String) postIdObj);
                } else if (postIdObj instanceof Number) {
                    postId = ((Number) postIdObj).intValue();
                } else {
                    return ResponseEntity.badRequest().body(Map.of("error", "postId must be a number"));
                }
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body(Map.of("error", "postId must be a valid number"));
            }

            if (postId <= 0) {
                return ResponseEntity.badRequest().body(Map.of("error", "postId must be a positive number"));
            }

            Post post = postRepository.findById(postId)
                    .orElse(null);

            if (post == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Post not found"));
            }

            FavoriteId favoriteId = new FavoriteId(user.getId(), postId);
            if (favoriteRepository.existsById(favoriteId)) {
                return ResponseEntity.badRequest().body(Map.of("error", "Already favorited"));
            }

            Favorite favorite = new Favorite();
            favorite.setId(favoriteId);
            favorite.setUser(user); 
            favorite.setPost(post);  

            favoriteRepository.save(favorite);

            return ResponseEntity.ok(Map.of(
                    "message", "Added to favorites",
                    "postId", postId
            ));
        } catch (Exception e) {
            System.err.println("Error adding favorite: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", "Internal server error: " + e.getMessage()));
        }
    }

    @PostMapping("/remove")
    public ResponseEntity<?> removeFavorite(@RequestBody Map<String, Object> payload, Authentication auth) {
        try {
            if (auth == null || auth.getPrincipal() == null) {
                return ResponseEntity.status(401).body(Map.of("error", "Authentication required"));
            }

            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String email = userDetails.getUsername();

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Object postIdObj = payload.get("postId");
            if (postIdObj == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "postId is required"));
            }

            Integer postId;
            try {
                if (postIdObj instanceof String) {
                    postId = Integer.parseInt((String) postIdObj);
                } else if (postIdObj instanceof Number) {
                    postId = ((Number) postIdObj).intValue();
                } else {
                    return ResponseEntity.badRequest().body(Map.of("error", "postId must be a number"));
                }
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body(Map.of("error", "postId must be a valid number"));
            }

            if (postId <= 0) {
                return ResponseEntity.badRequest().body(Map.of("error", "postId must be a positive number"));
            }

            FavoriteId favoriteId = new FavoriteId(user.getId(), postId);
            if (favoriteRepository.existsById(favoriteId)) {
                favoriteRepository.deleteById(favoriteId);
                return ResponseEntity.ok(Map.of(
                        "message", "Removed from favorites",
                        "postId", postId
                ));
            } else {
                return ResponseEntity.badRequest().body(Map.of("error", "Not in favorites"));
            }
        } catch (Exception e) {
            System.err.println("Error removing favorite: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", "Internal server error: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getUserFavorites(Authentication auth) {
        try {
            if (auth == null || auth.getPrincipal() == null) {
                return ResponseEntity.status(401).build();
            }

            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String email = userDetails.getUsername();

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            List<Favorite> favorites = favoriteRepository.findByIdUserId(user.getId());

            List<PostResponse> favoriteProperties = favorites.stream()
                    .map(favorite -> PostMapper.toResponse(favorite.getPost()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(favoriteProperties);
        } catch (Exception e) {
            System.err.println("Error getting user favorites: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/check/{postId}")
    public ResponseEntity<Map<String, Boolean>> checkFavorite(@PathVariable Integer postId, Authentication auth) {
        try {
            if (auth == null || auth.getPrincipal() == null) {
                return ResponseEntity.ok(Map.of("isFavorited", false));
            }

            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String email = userDetails.getUsername();

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (postId == null || postId <= 0) {
                return ResponseEntity.ok(Map.of("isFavorited", false));
            }

            FavoriteId favoriteId = new FavoriteId(user.getId(), postId);
            boolean isFavorited = favoriteRepository.existsById(favoriteId);

            return ResponseEntity.ok(Map.of("isFavorited", isFavorited));
        } catch (Exception e) {
            System.err.println("Error checking favorite: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(Map.of("isFavorited", false));
        }
    }

    @DeleteMapping("/clear")
    public ResponseEntity<?> clearAllFavorites(Authentication auth) {
        try {
            // Validate authentication
            if (auth == null || auth.getPrincipal() == null) {
                return ResponseEntity.status(401).body(Map.of("error", "Authentication required"));
            }

            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String email = userDetails.getUsername();

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            favoriteRepository.deleteByIdUserId(user.getId());

            return ResponseEntity.ok(Map.of("message", "All favorites cleared"));
        } catch (Exception e) {
            System.err.println("Error clearing favorites: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", "Internal server error: " + e.getMessage()));
        }
    }
}