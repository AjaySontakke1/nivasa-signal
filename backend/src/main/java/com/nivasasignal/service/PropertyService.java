package com.nivasasignal.service;

import com.nivasasignal.entity.Property;
import com.nivasasignal.enums.AvailabilityStatus;
import com.nivasasignal.enums.PropertyType;
import com.nivasasignal.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;

    // Save a new property
    public Property createProperty(Property property) {
        return propertyRepository.save(property);
    }

    // Get all properties
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    // Get a property by ID
    public Property getPropertyById(Long id) {
        return propertyRepository.findById(id).orElse(null);
    }

    // Find properties by city
    public List<Property> getPropertiesByCity(String city) {
        return propertyRepository.findByCityIgnoreCase(city);
    }

    // Find properties by type
    public List<Property> getPropertiesByType(PropertyType propertyType) {
        return propertyRepository.findByPropertyType(propertyType);
    }

    // Find properties by status
    public List<Property> getPropertiesByStatus(AvailabilityStatus availabilityStatus) {
        return propertyRepository.findByAvailabilityStatus(availabilityStatus);
    }
}
