package com.homespot.repository;

import com.homespot.model.AgentReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface AgentReviewRepository extends JpaRepository<AgentReview, Long> {
    
    // This is the new method that finds reviews by AgentProfile ID
    @Query("SELECT ar FROM AgentReview ar " +
           "JOIN AgentProfile ap ON ar.agent.id = ap.user.id " +
           "WHERE ap.id = :agentProfileId " +
           "ORDER BY ar.createdAt DESC")
    List<AgentReview> findByAgentIdOrderByCreatedAtDesc(@Param("agentProfileId") Long agentProfileId);
}