package com.homespot.repository;

import com.homespot.model.AgentHistory;
import com.homespot.model.Estate;
import com.homespot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AgentHistoryRepository extends JpaRepository<AgentHistory, Long> {
    
    // Find current active assignment for an estate (where unassignedAt is null)
    Optional<AgentHistory> findByEstateAndUnassignedAtIsNull(Estate estate);
    
    // Find all assignments for a specific estate
    List<AgentHistory> findByEstateOrderByAssignedAtDesc(Estate estate);
    
    // Find all assignments for a specific agent
    List<AgentHistory> findByAgentOrderByAssignedAtDesc(User agent);
    
    // Find current active assignments for an agent
    List<AgentHistory> findByAgentAndUnassignedAtIsNull(User agent);
    
    // Find assignments within a date range
    @Query("SELECT ah FROM AgentHistory ah WHERE ah.assignedAt BETWEEN :startDate AND :endDate ORDER BY ah.assignedAt DESC")
    List<AgentHistory> findByAssignedAtBetween(@Param("startDate") LocalDateTime startDate, 
                                              @Param("endDate") LocalDateTime endDate);
    
    // Find assignments by estate owner ID (fixed to use ownerId instead of owner)
    @Query("SELECT ah FROM AgentHistory ah WHERE ah.estate.ownerId = :ownerId ORDER BY ah.assignedAt DESC")
    List<AgentHistory> findByEstateOwnerId(@Param("ownerId") Integer ownerId);
    
    // Count active assignments for an agent
    @Query("SELECT COUNT(ah) FROM AgentHistory ah WHERE ah.agent = :agent AND ah.unassignedAt IS NULL")
    long countActiveAssignmentsByAgent(@Param("agent") User agent);
    
    // Find the most recent assignment for an estate
    @Query("SELECT ah FROM AgentHistory ah WHERE ah.estate = :estate ORDER BY ah.assignedAt DESC LIMIT 1")
    Optional<AgentHistory> findMostRecentByEstate(@Param("estate") Estate estate);
}