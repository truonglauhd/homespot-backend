package com.homespot.repository;

import com.homespot.model.Estate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EstateRepository extends JpaRepository<Estate, Integer> {

    @Query("""
           SELECT e FROM Estate e
           JOIN e.address a
           JOIN a.ward w
           JOIN w.province p
           WHERE (:estateTypeId IS NULL OR e.estateType.id = :estateTypeId)
             AND (:provinceId   IS NULL OR p.id = :provinceId)
             AND (:wardId       IS NULL OR w.id = :wardId)
           """)
    Page<Estate> search(
            @Param("estateTypeId") Integer estateTypeId,
            @Param("provinceId") Integer provinceId,
            @Param("wardId") Integer wardId,
            Pageable pageable
    );
    // Add these methods to your EstateRepository interface:

// Find estates by agent ID
List<Estate> findByAgentId(Integer agentId);

// Count methods for stats
@Query("SELECT COUNT(e) FROM Estate e WHERE e.status = :status")
Integer countByStatus(@Param("status") String status);

@Query("SELECT COUNT(e) FROM Estate e WHERE e.agentId = :agentId")
Integer countByAgentId(@Param("agentId") Integer agentId);

@Query("SELECT COUNT(e) FROM Estate e WHERE e.agentId = :agentId AND e.status = :status")
Integer countByAgentIdAndStatus(@Param("agentId") Integer agentId, @Param("status") String status);

    // Find estates by user ID for property management with all related data
    @Query("SELECT e FROM Estate e " +
            "WHERE e.ownerId = :ownerId")
    List<Estate> findByOwnerId(@Param("ownerId") Integer ownerId);

}