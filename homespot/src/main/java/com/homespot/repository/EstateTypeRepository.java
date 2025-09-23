package com.homespot.repository;

import com.homespot.model.EstateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstateTypeRepository extends JpaRepository<EstateType, Integer> {
    List<EstateType> findAll();
}
