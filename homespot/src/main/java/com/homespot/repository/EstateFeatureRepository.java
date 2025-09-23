package com.homespot.repository;

import com.homespot.model.EstateFeature;
import com.homespot.model.EstateFeatureId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstateFeatureRepository extends JpaRepository<EstateFeature, EstateFeatureId> {}


