package com.nivasasignal.property;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Transactional
    public Property createProperty(Property property) {
        return propertyRepository.save(property);
    }

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    public Property getPropertyById(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Property not found with id: " + id));
    }

    public List<Property> getPropertiesByCity(String city) {
        return propertyRepository.findByCityIgnoreCase(city);
    }

    public List<Property> getPropertiesByType(PropertyType propertyType) {
        return propertyRepository.findByPropertyType(propertyType);
    }

    public List<Property> getPropertiesByStatus(AvailabilityStatus availabilityStatus) {
        return propertyRepository.findByAvailabilityStatus(availabilityStatus);
    }
}
