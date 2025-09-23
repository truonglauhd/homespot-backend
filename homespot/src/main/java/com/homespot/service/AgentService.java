package com.homespot.service;

import com.homespot.dto.AgentDTO;
import com.homespot.model.AgentProfile;
import com.homespot.repository.AgentProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgentService {
    
    @Autowired
    private AgentProfileRepository agentProfileRepository;

    public List<AgentDTO> getAllAgents() {
    List<AgentProfile> profiles = agentProfileRepository.findAllAgents();
    System.out.println("=== DEBUG: Raw AgentProfile data ===");
    for (AgentProfile profile : profiles) {
        System.out.println("AgentProfile ID: " + profile.getId() + ", User ID: " + profile.getUser().getId() + ", Name: " + profile.getUser().getName());
    }
    
    List<AgentDTO> result = profiles.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
            
    System.out.println("=== DEBUG: Converted AgentDTO data ===");
    for (AgentDTO dto : result) {
        System.out.println("AgentDTO ID: " + dto.getId() + ", Name: " + dto.getName());
    }
    
    return result;
}

    public AgentDTO getAgentById(Long id) {
        AgentProfile agent = agentProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agent not found"));
        return convertToDTO(agent);
    }

    private AgentDTO convertToDTO(AgentProfile agent) {
        return new AgentDTO(
                agent.getUser().getId().longValue(),
                agent.getUser().getName(),
                agent.getUser().getEmail(),
                agent.getUser().getPhone(),
                agent.getProfilePicture(),
                agent.getBio(),
                agent.getExperienceYears(),
                agent.getSuccessfulSales()
        );
    }
}