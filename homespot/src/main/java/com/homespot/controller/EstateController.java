package com.homespot.controller;

import com.homespot.dto.EstateDTO;
import com.homespot.service.EstateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estates")
public class EstateController {
    @Autowired private EstateService estateService;

    @GetMapping
    public List<EstateDTO> getAllEstates() {
        return estateService.getAllEstates();
    }

    @GetMapping("/{id}")
    public EstateDTO getEstate(@PathVariable Integer id) {
        return estateService.getEstateById(id);
    }
}
