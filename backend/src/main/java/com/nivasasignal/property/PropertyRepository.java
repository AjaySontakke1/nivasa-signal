package com.nivasasignal.property;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    // Find properties by city
    List<Property> findByCity(String city);

    // Find properties by city (case-insensitive)
    List<Property> findByCityIgnoreCase(String city);

    // Find properties by type (FLAT, VILLA, etc.)
    List<Property> findByPropertyType(PropertyType propertyType);

    // Find properties by availability status (AVAILABLE, SOLD, etc.)
    List<Property> findByAvailabilityStatus(AvailabilityStatus availabilityStatus);
}
