package com.homespot.controller;

import com.homespot.dto.AgentDTO;
import com.homespot.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/agents")
public class AgentController {
    
    @Autowired 
    private AgentService agentService;

    @GetMapping
    public List<AgentDTO> getAllAgents() {
        return agentService.getAllAgents();
    }

    @GetMapping("/{id}")
    public AgentDTO getAgent(@PathVariable Long id) {
        return agentService.getAgentById(id);
    }
    
}
