package com.homespot.mapper;

import com.homespot.dto.PostDetailResponse;
import com.homespot.model.Amenity;
import com.homespot.model.EstateFeature;
import com.homespot.model.Post;

import java.util.List;
import java.util.stream.Collectors;

public class PostDetailMapper {
    
    public static PostDetailResponse toDetailResponse(Post post) {
        PostDetailResponse dto = new PostDetailResponse();
        
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setPrice(post.getPrice());
        dto.setPostAt(post.getPostAt());
        dto.setExpiredAt(post.getExpiredAt());
        
        if (post.getEstate() != null) {
            dto.setEstateId(post.getEstate().getId());
            dto.setArea(post.getEstate().getArea());
            
            if (post.getEstate().getEstateType() != null) {
                dto.setEstateTypeId(post.getEstate().getEstateType().getId());
                dto.setEstateTypeName(post.getEstate().getEstateType().getName());
            }
            
            if (post.getEstate().getAddress() != null) {
                dto.setAddressId(post.getEstate().getAddress().getId());
                dto.setAddress(post.getEstate().getAddress().getName());
            }
            
            if (post.getEstate().getImages() != null && !post.getEstate().getImages().isEmpty()) {
                dto.setImageUrl(post.getEstate().getImages().get(0).getImageUrl());
            }
            
            if (post.getEstate().getAmenities() != null) {
                List<PostDetailResponse.AmenityDTO> amenities = post.getEstate().getAmenities().stream()
                        .map(amenity -> new PostDetailResponse.AmenityDTO(amenity.getId(), amenity.getName()))
                        .collect(Collectors.toList());
                dto.setAmenities(amenities);
            }
            
            if (post.getEstate().getFeatures() != null) {
                List<PostDetailResponse.FeatureDTO> features = post.getEstate().getFeatures().stream()
                        .map(estateFeature -> new PostDetailResponse.FeatureDTO(
                                estateFeature.getFeature().getId(), 
                                estateFeature.getFeature().getName(),
                                estateFeature.getValue()))
                        .collect(Collectors.toList());
                dto.setFeatures(features);
            }
        }
        
        if (post.getEstate() != null && post.getEstate().getAgentHistories() != null) {
            post.getEstate().getAgentHistories().stream()
                    .filter(history -> history.getUnassignedAt() == null)
                    .findFirst()
                    .ifPresent(history -> {
                        if (history.getAgent() != null) {
                            dto.setAgentId(history.getAgent().getId());
                            dto.setAgentName(history.getAgent().getName());
                        }
                    });
        }
        
        return dto;
    }
}
