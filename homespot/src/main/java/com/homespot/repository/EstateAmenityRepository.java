package com.homespot.repository;

import com.homespot.model.EstateAmenity;
import com.homespot.model.EstateAmenityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstateAmenityRepository extends JpaRepository<EstateAmenity, EstateAmenityId> {}


