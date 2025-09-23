package com.homespot.controller;

import com.homespot.dto.EstateDTO;
import com.homespot.service.EstateService;
import com.homespot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/agent")
public class AgentEstatesController {

    @Autowired
    private EstateService estateService;
    
    @Autowired
    private UserService userService;

    public AgentEstatesController() {
        System.out.println("=== AgentEstatesController CREATED (FIXED) ===");
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        System.out.println("=== AGENT TEST ENDPOINT CALLED ===");
        System.out.println("Current user: " + userService.getCurrentUser());
        System.out.println("Current user roles: " + userService.getCurrentUserRoles());
        System.out.println("Has Agent role: " + userService.hasRole("Agent"));
        System.out.println("Has Admin role: " + userService.hasRole("Admin"));
        return ResponseEntity.ok("Agent controller is working!");
    }

    @GetMapping("/estates")
    public ResponseEntity<?> getAgentEstates() {
        try {
            System.out.println("=== GET AGENT ESTATES CALLED ===");
            
            var currentUser = userService.getCurrentUser();
            if (currentUser == null) {
                return ResponseEntity.status(401)
                    .body(Map.of("error", "User not authenticated"));
            }
            
            Integer currentUserId = currentUser.getId();
            String currentUserEmail = currentUser.getEmail();
            List<String> userRoles = userService.getCurrentUserRoles();
            
            System.out.println("Current User ID: " + currentUserId);
            System.out.println("Current User Email: " + currentUserEmail);
            System.out.println("User Roles: " + userRoles);
            
            boolean hasAgentRole = userService.hasRole("Agent");
            boolean hasAdminRole = userService.hasRole("Admin");
            
            System.out.println("Has Agent role: " + hasAgentRole);
            System.out.println("Has Admin role: " + hasAdminRole);
            
            if (!hasAgentRole && !hasAdminRole) {
                return ResponseEntity.status(403)
                    .body(Map.of("error", "Access denied. Agent or Admin role required."));
            }
            
            List<EstateDTO> estates;
            
            if (hasAdminRole) {
                System.out.println("Admin user - getting all estates");
                estates = estateService.getAllEstates();
            } else {
                System.out.println("Agent user - getting assigned estates");
                estates = estateService.getEstatesByAssignedAgent(currentUserId);
            }
            
            System.out.println("Found " + estates.size() + " estates");
            
            return ResponseEntity.ok(Map.of(
                "message", "Estates retrieved successfully",
                "estates", estates,
                "count", estates.size(),
                "userRole", hasAdminRole ? "Admin" : "Agent",
                "userEmail", currentUserEmail
            ));
            
        } catch (Exception e) {
            System.err.println("Error in getAgentEstates: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500)
                .body(Map.of("error", "Failed to fetch estates: " + e.getMessage()));
        }
    }

    @GetMapping("/dashboard/stats")
    public ResponseEntity<?> getAgentStats() {
        try {
            System.out.println("=== GET AGENT STATS CALLED ===");
            
            var currentUser = userService.getCurrentUser();
            if (currentUser == null) {
                return ResponseEntity.status(401)
                    .body(Map.of("error", "User not authenticated"));
            }
            
            boolean hasAgentRole = userService.hasRole("Agent");
            boolean hasAdminRole = userService.hasRole("Admin");
            
            if (!hasAgentRole && !hasAdminRole) {
                return ResponseEntity.status(403)
                    .body(Map.of("error", "Access denied. Agent or Admin role required."));
            }
            
            Map<String, Object> stats = new HashMap<>();
            
            if (hasAdminRole) {
                System.out.println("Getting admin stats");
                stats.put("totalEstates", estateService.getTotalEstatesCount());
                stats.put("activeEstates", estateService.getActiveEstatesCount());
                stats.put("pendingEstates", estateService.getPendingEstatesCount());
                stats.put("soldEstates", estateService.getSoldEstatesCount());
                stats.put("message", "System-wide statistics");
            } else {
                System.out.println("Getting agent stats for user ID: " + currentUser.getId());
                Integer agentId = currentUser.getId();
                long totalAssigned = estateService.getAgentEstatesCount(agentId);
                long activeAssigned = estateService.getAgentActiveEstatesCount(agentId);
                long pendingAssigned = estateService.getAgentPendingEstatesCount(agentId);
                long soldAssigned = estateService.getAgentSoldEstatesCount(agentId);
                
                System.out.println("Agent stats - Total: " + totalAssigned + ", Active: " + activeAssigned);
                
                stats.put("totalAssigned", totalAssigned);
                stats.put("activeAssigned", activeAssigned);
                stats.put("pendingAssigned", pendingAssigned);
                stats.put("soldAssigned", soldAssigned);
                stats.put("message", "Agent assignment statistics");
            }
            
            stats.put("userRole", hasAdminRole ? "Admin" : "Agent");
            stats.put("userEmail", currentUser.getEmail());
            stats.put("userId", currentUser.getId());
            
            return ResponseEntity.ok(stats);
            
        } catch (Exception e) {
            System.err.println("Error in getAgentStats: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500)
                .body(Map.of("error", "Failed to fetch stats: " + e.getMessage()));
        }
    }
}