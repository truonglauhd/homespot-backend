package com.homespot.repository;

import com.homespot.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findByWardId(Integer wardId);
}


