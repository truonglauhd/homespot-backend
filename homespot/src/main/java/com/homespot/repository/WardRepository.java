package com.homespot.repository;

import com.homespot.model.Ward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WardRepository extends JpaRepository<Ward, Integer> {
    List<Ward> findByProvinceId(Integer provinceId);
}


