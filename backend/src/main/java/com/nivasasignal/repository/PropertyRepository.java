package com.nivasasignal.repository;

import com.nivasasignal.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query("""
            SELECT p FROM Property p
            WHERE (:city IS NULL OR LOWER(p.city) = LOWER(:city))
            AND (:locality IS NULL OR LOWER(p.locality) = LOWER(:locality))
            AND (:minPrice IS NULL OR p.priceInr >= :minPrice)
            AND (:maxPrice IS NULL OR p.priceInr <= :maxPrice)
            AND (:minAreaSqft IS NULL OR p.areaSqft >= :minAreaSqft)
            AND (:maxAreaSqft IS NULL OR p.areaSqft <= :maxAreaSqft)
            """)
    List<Property> searchProperties(
            @Param("city") String city,
            @Param("locality") String locality,
            @Param("minPrice") Long minPrice,
            @Param("maxPrice") Long maxPrice,
            @Param("minAreaSqft") BigDecimal minAreaSqft,
            @Param("maxAreaSqft") BigDecimal maxAreaSqft
    );
}
