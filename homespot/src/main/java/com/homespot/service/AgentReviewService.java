package com.homespot.service;

import com.homespot.dto.AgentReviewDTO;
import com.homespot.model.AgentReview;
import com.homespot.repository.AgentReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgentReviewService {
    
    @Autowired
    private AgentReviewRepository agentReviewRepository;

    public List<AgentReviewDTO> getReviewsByAgentId(Long agentId) {
    List<AgentReview> reviews = agentReviewRepository.findByAgentIdOrderByCreatedAtDesc(agentId);
    return reviews.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
}

    private AgentReviewDTO convertToDTO(AgentReview review) {
        AgentReviewDTO dto = new AgentReviewDTO();
        dto.setId(review.getId());
        dto.setReviewerName(review.getReviewer().getName());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setCreatedAt(review.getCreatedAt());
        return dto;
    }
}