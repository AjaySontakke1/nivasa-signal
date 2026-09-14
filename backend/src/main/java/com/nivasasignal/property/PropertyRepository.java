package com.nivasasignal.property;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    List<Property> findByCity(String city);

    List<Property> findByCityIgnoreCase(String city);

    List<Property> findByPropertyType(PropertyType propertyType);

    List<Property> findByAvailabilityStatus(AvailabilityStatus availabilityStatus);

    List<Property> findByCityIgnoreCaseAndPropertyType(String city, PropertyType propertyType);

    List<Property> findByCityIgnoreCaseAndAvailabilityStatus(String city, AvailabilityStatus availabilityStatus);
}
