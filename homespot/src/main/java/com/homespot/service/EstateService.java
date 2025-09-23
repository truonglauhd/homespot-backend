package com.homespot.service;

import com.homespot.dto.CreateEstateRequest;
import com.homespot.dto.EstateDTO;
import com.homespot.mapper.EstateMapper;
import com.homespot.model.Estate;
import com.homespot.model.EstateFeature;
import com.homespot.model.EstateFeatureId;
import com.homespot.model.EstateImage;
import com.homespot.model.EstateAmenity;
import com.homespot.model.EstateAmenityId;
import com.homespot.model.AgentHistory;
import com.homespot.model.User;
import com.homespot.repository.EstateRepository;
import com.homespot.repository.FeatureRepository;
import com.homespot.repository.EstateImageRepository;
import com.homespot.repository.EstateAmenityRepository;
import com.homespot.repository.EstateFeatureRepository;
import com.homespot.repository.AmenityRepository;
import com.homespot.repository.AgentHistoryRepository;
import com.homespot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstateService {
    @Autowired private EstateRepository estateRepository;
    @Autowired(required = false) private EstateImageRepository estateImageRepository;
    @Autowired private FeatureRepository featureRepository;
    @Autowired private EstateAmenityRepository estateAmenityRepository;
    @Autowired private EstateFeatureRepository estateFeatureRepository;
    @Autowired private AmenityRepository amenityRepository;
    @Autowired private AgentHistoryRepository agentHistoryRepository;
    @Autowired private UserRepository userRepository;

    // Existing methods
    public List<EstateDTO> getAllEstates() {
        return estateRepository.findAll()
                .stream()
                .map(EstateMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<EstateDTO> convertEstatesToDTOs(List<Estate> estates) {
        return estates.stream()
                .map(EstateMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<EstateDTO> getEstatesByUserId(Integer userId) {
        List<Estate> estates = estateRepository.findByOwnerId(userId);
        return estates.stream()
                .map(EstateMapper::toDTO)
                .collect(Collectors.toList());
    }

    // NEW: Agent-specific methods using AgentHistory
    
    /**
     * Get estates currently assigned to a specific agent via AgentHistory
     */
    public List<EstateDTO> getEstatesByAssignedAgent(Integer agentId) {
        System.out.println("=== getEstatesByAssignedAgent DEBUG ===");
        System.out.println("Looking for agent ID: " + agentId);
        
        User agent = userRepository.findById(agentId).orElse(null);
        System.out.println("Found agent: " + (agent != null ? agent.getEmail() : "NULL"));
        
        if (agent == null) {
            System.out.println("Agent not found in database!");
            return List.of();
        }
        
        List<AgentHistory> activeAssignments = agentHistoryRepository
                .findByAgentAndUnassignedAtIsNull(agent);
        
        System.out.println("Active assignments found: " + activeAssignments.size());
        for (AgentHistory ah : activeAssignments) {
            System.out.println("Assignment - Estate ID: " + ah.getEstate().getId() + 
                              ", Agent: " + ah.getAgent().getEmail());
        }
        
        List<EstateDTO> result = activeAssignments.stream()
                .map(history -> EstateMapper.toDTO(history.getEstate()))
                .collect(Collectors.toList());
                
        System.out.println("Returning " + result.size() + " estates");
        System.out.println("=======================================");
        
        return result;
    }

    /**
     * Count estates currently assigned to a specific agent
     */
    public long countEstatesByAssignedAgent(Integer agentId) {
        User agent = userRepository.findById(agentId).orElse(null);
        if (agent == null) {
            return 0;
        }
        
        return agentHistoryRepository.countActiveAssignmentsByAgent(agent);
    }

    /**
     * Get estates by assigned agent with status filter
     */
    public List<EstateDTO> getEstatesByAssignedAgentAndStatus(Integer agentId, String status) {
        User agent = userRepository.findById(agentId).orElse(null);
        if (agent == null) {
            return List.of();
        }
        
        List<AgentHistory> activeAssignments = agentHistoryRepository
                .findByAgentAndUnassignedAtIsNull(agent);
        
        return activeAssignments.stream()
                .map(AgentHistory::getEstate)
                .filter(estate -> status == null || status.equalsIgnoreCase(estate.getStatus()))
                .map(EstateMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Assign an agent to an estate
     */
    @Transactional
    public boolean assignAgentToEstate(Integer estateId, Integer agentId) {
        Estate estate = estateRepository.findById(estateId).orElse(null);
        User agent = userRepository.findById(agentId).orElse(null);
        
        if (estate == null || agent == null) {
            return false;
        }
        
        // Check if there's already an active assignment
        if (agentHistoryRepository.findByEstateAndUnassignedAtIsNull(estate).isPresent()) {
            return false; // Estate already has an active agent
        }
        
        // Create new assignment
        AgentHistory assignment = new AgentHistory(estate, agent);
        agentHistoryRepository.save(assignment);
        
        return true;
    }

    /**
     * Unassign an agent from an estate
     */
    @Transactional
    public boolean unassignAgentFromEstate(Integer estateId, Integer agentId) {
        Estate estate = estateRepository.findById(estateId).orElse(null);
        User agent = userRepository.findById(agentId).orElse(null);
        
        if (estate == null || agent == null) {
            return false;
        }
        
        // Find active assignment
        var activeAssignment = agentHistoryRepository.findByEstateAndUnassignedAtIsNull(estate);
        
        if (activeAssignment.isPresent() && 
            activeAssignment.get().getAgent().getId().equals(agentId)) {
            
            activeAssignment.get().unassign();
            agentHistoryRepository.save(activeAssignment.get());
            return true;
        }
        
        return false;
    }

    // STATS METHODS FOR AGENTS

    /**
     * Get agent-specific estates count (currently assigned)
     */
    public long getAgentEstatesCount(Integer agentId) {
        return countEstatesByAssignedAgent(agentId);
    }

    /**
     * Get agent-specific active estates count
     */
    public long getAgentActiveEstatesCount(Integer agentId) {
        return getEstatesByAssignedAgentAndStatus(agentId, "ACTIVE").size();
    }

    /**
     * Get agent-specific pending estates count
     */
    public long getAgentPendingEstatesCount(Integer agentId) {
        return getEstatesByAssignedAgentAndStatus(agentId, "PENDING").size();
    }

    /**
     * Get agent-specific sold estates count
     */
    public long getAgentSoldEstatesCount(Integer agentId) {
        return getEstatesByAssignedAgentAndStatus(agentId, "SOLD").size();
    }

    // Existing update/delete methods
    public EstateDTO updateEstate(Integer estateId, Integer userId, CreateEstateRequest request) {
        Estate estate = estateRepository.findById(estateId)
                .orElseThrow(() -> new RuntimeException("Estate not found"));

        if (!estate.getOwnerId().equals(userId)) {
            throw new RuntimeException("Not authorized to update this estate");
        }

        estate.setEstateTypeId(request.estateTypeId);
        estate.setAddressId(request.addressId);
        estate.setArea(request.area);

        Estate savedEstate = estateRepository.save(estate);
        return EstateMapper.toDTO(savedEstate);
    }

    public void deleteEstate(Integer estateId, Integer userId) {
        Estate estate = estateRepository.findById(estateId)
                .orElseThrow(() -> new RuntimeException("Estate not found"));

        if (!estate.getOwnerId().equals(userId)) {
            throw new RuntimeException("Not authorized to delete this estate");
        }

        estateRepository.delete(estate);
    }

    public EstateDTO getEstateById(Integer id) {
        return estateRepository.findById(id)
                .map(EstateMapper::toDTO)
                .orElse(null);
    }

    /**
     * Update estate for agents (simplified version)
     */
    public Estate updateEstate(Integer id, Estate updatedEstate) {
        Estate estate = estateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estate not found"));

        // Update fields that agents can modify
        if (updatedEstate.getStatus() != null) {
            estate.setStatus(updatedEstate.getStatus());
        }
        if (updatedEstate.getDescription() != null) {
            estate.setDescription(updatedEstate.getDescription());
        }
        if (updatedEstate.getPrice() != null) {
            estate.setPrice(updatedEstate.getPrice());
        }

        return estateRepository.save(estate);
    }

    /**
     * Delete estate completely (admin only)
     */
    public void deleteEstate(Integer id) {
        estateRepository.deleteById(id);
    }

    // SYSTEM STATS METHODS

    /**
     * Get total estates count (system-wide)
     */
    public Integer getTotalEstatesCount() {
        return (int) estateRepository.count();
    }

    /**
     * Get active estates count (system-wide)
     */
    public Integer getActiveEstatesCount() {
        return estateRepository.countByStatus("ACTIVE");
    }

    /**
     * Get pending estates count (system-wide)
     */
    public Integer getPendingEstatesCount() {
        return estateRepository.countByStatus("PENDING");
    }

    /**
     * Get sold estates count (system-wide)
     */
    public Integer getSoldEstatesCount() {
        return estateRepository.countByStatus("SOLD");
    }

    // Get estate entity by ID (for agent operations)
    public Estate getEstateEntityById(Integer id) {
        return estateRepository.findById(id).orElse(null);
    }

    // Existing createEstate method
    @Transactional
    public Estate createEstate(Integer ownerId, CreateEstateRequest req) {
        Estate estate = new Estate();
        estate.setOwnerId(ownerId);
        estate.setEstateTypeId(req.estateTypeId);
        estate.setAddressId(req.addressId);
        estate.setArea(req.area);
        estate.setStatus("ACTIVE"); // Default status
        estate = estateRepository.save(estate);

        if (req.imageUrls != null && estateImageRepository != null) {
            List<EstateImage> images = new ArrayList<>();
            for (String url : req.imageUrls) {
                EstateImage img = new EstateImage();
                img.setEstateId(estate.getId());
                img.setImageUrl(url);
                images.add(estateImageRepository.save(img));
            }
            estate.setImages(images);
        }

        if (req.features != null && !req.features.isEmpty()) {
            List<EstateFeature> featureLinks = new ArrayList<>();
            for (CreateEstateRequest.FeatureValue fv : req.features) {
                EstateFeature ef = new EstateFeature();
                EstateFeatureId id = new EstateFeatureId();
                id.setEstateId(estate.getId());
                id.setFeatureId(fv.id);
                ef.setId(id);
                ef.setEstate(estate);
                ef.setFeature(featureRepository.findById(fv.id).orElse(null));
                ef.setValue(fv.value);
                featureLinks.add(ef);
                estateFeatureRepository.save(ef);
            }
            estate.setFeatures(featureLinks);
        }

        if (req.amenityIds != null && !req.amenityIds.isEmpty()) {
            for (Integer amenityId : req.amenityIds) {
                EstateAmenity ea = new EstateAmenity();
                EstateAmenityId id = new EstateAmenityId();
                id.setEstateId(estate.getId());
                id.setAmenityId(amenityId);
                ea.setId(id);
                ea.setEstate(estate);
                ea.setAmenity(amenityRepository.findById(amenityId).orElse(null));
                estateAmenityRepository.save(ea);
            }
        }

        return estate;
    }
}