package com.homespot.repository;

import com.homespot.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("""
           SELECT p FROM Post p
           JOIN p.estate e
           JOIN e.address a
           JOIN a.ward w
           JOIN w.province pr
           WHERE (:estateTypeId IS NULL OR e.estateType.id = :estateTypeId)
             AND (:provinceId   IS NULL OR pr.id = :provinceId)
             AND (:wardId       IS NULL OR w.id = :wardId)
             AND (:minPrice     IS NULL OR p.price >= :minPrice)
             AND (:maxPrice     IS NULL OR p.price <= :maxPrice)
           """)
    Page<Post> search(
            @Param("estateTypeId") Integer estateTypeId,
            @Param("provinceId") Integer provinceId,
            @Param("wardId") Integer wardId,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            Pageable pageable
    );
}
