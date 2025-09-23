package com.homespot.repository;

import com.homespot.model.Favorite;
import com.homespot.model.FavoriteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {
    
    // Find all favorites for a specific user
    List<Favorite> findByIdUserId(Integer userId);
    
    // Check if a user has favorited a specific post
    boolean existsByIdUserIdAndIdPostId(Integer userId, Integer postId);
    
    // Delete all favorites for a user
    void deleteByIdUserId(Integer userId);
    
    // Count favorites for a user
    long countByIdUserId(Integer userId);
    
    // Custom query to get favorites with post details
    @Query("SELECT f FROM Favorite f JOIN FETCH f.post p WHERE f.id.userId = :userId")
    List<Favorite> findFavoritesWithPostByUserId(@Param("userId") Integer userId);
}