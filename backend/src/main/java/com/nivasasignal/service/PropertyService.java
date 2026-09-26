package com.nivasasignal.service;

import com.nivasasignal.dto.CreatePropertyRequest;
import com.nivasasignal.dto.PropertyResponse;
import com.nivasasignal.entity.Property;
import com.nivasasignal.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public PropertyResponse createProperty(CreatePropertyRequest request) {
        Property property = new Property();
        property.setTitle(request.title());
        property.setPropertyType(request.propertyType());
        property.setBhk(request.bhk());
        property.setCity(request.city());
        property.setLocality(request.locality());
        property.setAddress(request.address());
        property.setPriceInr(request.priceInr());
        property.setAreaSqft(request.areaSqft());
        property.setAvailabilityStatus(request.availabilityStatus());
        property.setLastCheckedAt(LocalDateTime.now());

        Property savedProperty = propertyRepository.save(property);
        return toResponse(savedProperty);
    }

    public List<PropertyResponse> getAllProperties() {
        List<Property> properties = propertyRepository.findAll();
        List<PropertyResponse> responseList = new ArrayList<>();

        for (Property property : properties) {
            responseList.add(toResponse(property));
        }

        return responseList;
    }

    public PropertyResponse getPropertyById(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found"));

        return toResponse(property);
    }

    public List<PropertyResponse> searchProperties(
            String city,
            String locality,
            Long minPrice,
            Long maxPrice,
            BigDecimal minAreaSqft,
            BigDecimal maxAreaSqft
    ) {
        List<PropertyResponse> results = new ArrayList<>();

        for (Property property : propertyRepository.findAll()) {

            if (city != null
                    && !property.getCity().equalsIgnoreCase(city)) {
                continue;
            }

            if (locality != null
                    && !property.getLocality().equalsIgnoreCase(locality)) {
                continue;
            }

            if (minPrice != null
                    && property.getPriceInr() < minPrice) {
                continue;
            }

            if (maxPrice != null
                    && property.getPriceInr() > maxPrice) {
                continue;
            }

            if (minAreaSqft != null
                    && property.getAreaSqft().compareTo(minAreaSqft) < 0) {
                continue;
            }

            if (maxAreaSqft != null
                    && property.getAreaSqft().compareTo(maxAreaSqft) > 0) {
                continue;
            }

            results.add(toResponse(property));
        }

        return results;
    }

    private PropertyResponse toResponse(Property property) {
        return new PropertyResponse(
                property.getId(),
                property.getTitle(),
                property.getPropertyType(),
                property.getBhk(),
                property.getCity(),
                property.getLocality(),
                property.getAddress(),
                property.getPriceInr(),
                property.getAreaSqft(),
                property.getAvailabilityStatus(),
                property.getTruthScore(),
                property.getLastCheckedAt(),
                property.getCreatedAt(),
                property.getUpdatedAt()
        );
    }
}
