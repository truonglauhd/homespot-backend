package com.homespot.repository;

import com.homespot.model.AgentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AgentProfileRepository extends JpaRepository<AgentProfile, Long> {
    
    @Query("SELECT ap FROM AgentProfile ap " +
           "JOIN ap.user u " +
           "JOIN u.userRoles ur " +
           "WHERE ur.id.roleId = 3") // roleId 3 is agent
    List<AgentProfile> findAllAgents();
    
    Optional<AgentProfile> findByUserId(Integer userId);
}