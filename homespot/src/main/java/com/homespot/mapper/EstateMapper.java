package com.homespot.mapper;

import com.homespot.dto.EstateDTO;
import com.homespot.dto.FeatureDTO;
import com.homespot.dto.AmenityDTO;
import com.homespot.model.Estate;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class EstateMapper {
    public static EstateDTO toDTO(Estate estate) {
        EstateDTO dto = new EstateDTO();

        // Basic fields
        dto.setId(estate.getId());
        dto.setArea(estate.getArea());
        dto.setCreatedAt(estate.getCreatedAt());
        dto.setUserId(estate.getOwnerId());

        // THE MISSING FIELD THAT BROKE YOUR FILTER:
        dto.setEstateTypeId(estate.getEstateTypeId());

        // Set estate type name if relationship is loaded
        if (estate.getEstateType() != null) {
            dto.setEstateType(estate.getEstateType().getName());
        }

        // Set address information if relationship is loaded
        if (estate.getAddress() != null) {
            // You might want to build a full address string here
            dto.setAddress(estate.getAddress().getName());
            dto.setLocation(estate.getAddress().getName()); // or build full location string
        }

        // Set owner name - you'll need to add this logic based on your User entity
        // For now, setting to null, but you might want to fetch this
        dto.setOwnerName(null);

        // Set image URL from first image in the list
        if (estate.getImages() != null && !estate.getImages().isEmpty()) {
            dto.setImageUrl(estate.getImages().get(0).getImageUrl());
        } else {
            dto.setImageUrl(null);
        }

        // Map features
        if (estate.getFeatures() != null && !estate.getFeatures().isEmpty()) {
            List<FeatureDTO> featureDTOs = estate.getFeatures().stream()
                    .map(ef -> new FeatureDTO(
                            ef.getFeature().getId(),
                            ef.getFeature().getName(),
                            ef.getValue()
                    ))
                    .collect(Collectors.toList());
            dto.setFeatures(featureDTOs);
        } else {
            dto.setFeatures(new ArrayList<>());
        }

        // Map amenities
        if (estate.getAmenities() != null && !estate.getAmenities().isEmpty()) {
            List<AmenityDTO> amenityDTOs = estate.getAmenities().stream()
                    .map(amenity -> new AmenityDTO(amenity.getId(), amenity.getName()))
                    .collect(Collectors.toList());
            dto.setAmenities(amenityDTOs);
        } else {
            dto.setAmenities(new ArrayList<>());
        }

        // Add other fields that are missing:
        dto.setTitle(null);           // Add if you have title in Estate
        dto.setDescription(null);     // Add if you have description in Estate
        dto.setPrice(null);           // Add if you have price in Estate
        dto.setBedrooms(null);        // Add if you have bedrooms in Estate
        dto.setBathrooms(null);       // Add if you have bathrooms in Estate

        return dto;
    }
}