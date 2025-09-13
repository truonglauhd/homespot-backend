package com.homespot.mapper;

import com.homespot.dto.EstateDTO;
import com.homespot.model.Estate;

public class EstateMapper {
    public static EstateDTO toDTO(Estate estate) {
        EstateDTO dto = new EstateDTO();
        dto.setId(estate.getId());
        dto.setArea(estate.getArea());
        dto.setCreatedAt(estate.getCreatedAt());
        dto.setOwnerName(null);
        dto.setEstateType(null);
        dto.setAddress(null);
        return dto;
    }
}
