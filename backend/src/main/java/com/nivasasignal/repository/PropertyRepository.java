package com.nivasasignal.repository;

import com.nivasasignal.entity.Property;
import com.nivasasignal.enums.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    Optional<Property> findFirstByTitleIgnoreCaseAndCityIgnoreCaseAndLocalityIgnoreCaseAndPropertyTypeAndBhk(
            String title,
            String city,
            String locality,
            PropertyType propertyType,
            String bhk
    );

    List<Property> findByCityIgnoreCaseAndLocalityIgnoreCaseAndPropertyTypeAndBhk(
            String city,
            String locality,
            PropertyType propertyType,
            String bhk
    );
}
