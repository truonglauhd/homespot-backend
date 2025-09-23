package com.homespot.controller;

import com.homespot.dto.AgentReviewDTO;
import com.homespot.service.AgentReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/agents")
public class AgentReviewController {
    
    @Autowired 
    private AgentReviewService agentReviewService;

    @GetMapping("/{agentId}/reviews")
public List<AgentReviewDTO> getAgentReviews(@PathVariable Long agentId) {
    return agentReviewService.getReviewsByAgentId(agentId);
}
}