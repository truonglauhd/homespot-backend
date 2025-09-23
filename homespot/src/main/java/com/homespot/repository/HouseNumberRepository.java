package com.homespot.repository;

import com.homespot.model.HouseNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseNumberRepository extends JpaRepository<HouseNumber, Integer> {
    List<HouseNumber> findByAddressId(Integer addressId);
}


