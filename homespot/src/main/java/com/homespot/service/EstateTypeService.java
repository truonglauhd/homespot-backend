package com.homespot.service;

import com.homespot.model.EstateType;
import com.homespot.repository.EstateTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstateTypeService {
    
    @Autowired
    private EstateTypeRepository estateTypeRepository;
    
    public List<EstateType> getAllEstateTypes() {
        return estateTypeRepository.findAll();
    }
}
