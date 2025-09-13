package com.homespot.service;

import com.homespot.dto.EstateDTO;
import com.homespot.mapper.EstateMapper;
import com.homespot.repository.EstateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstateService {
    @Autowired private EstateRepository estateRepository;

    public List<EstateDTO> getAllEstates() {
        return estateRepository.findAll()
                .stream()
                .map(EstateMapper::toDTO)
                .collect(Collectors.toList());
    }

    public EstateDTO getEstateById(Integer id) {
        return estateRepository.findById(id)
                .map(EstateMapper::toDTO)
                .orElse(null);
    }
}
