package com.homespot.repository;

import com.homespot.model.Estate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EstateRepository extends JpaRepository<Estate, Integer> {

    @Query("""
           SELECT e FROM Estate e
           JOIN e.address a
           JOIN a.ward w
           JOIN w.province p
           WHERE (:estateTypeId IS NULL OR e.estateType.id = :estateTypeId)
             AND (:provinceId   IS NULL OR p.id = :provinceId)
             AND (:wardId       IS NULL OR w.id = :wardId)
           """)
    Page<Estate> search(
            @Param("estateTypeId") Integer estateTypeId,
            @Param("provinceId") Integer provinceId,
            @Param("wardId") Integer wardId,
            Pageable pageable
    );
}
