package com.nivasasignal.service;

import com.nivasasignal.dto.CreatePropertyRequest;
import com.nivasasignal.dto.PropertyPageResponse;
import com.nivasasignal.dto.PropertyResponse;
import com.nivasasignal.entity.Property;
import com.nivasasignal.enums.AvailabilityStatus;
import com.nivasasignal.enums.PropertyType;
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

    // 1. Create and save a new property
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

    // 2. Get all properties
    public List<PropertyResponse> getAllProperties() {
        List<Property> properties = propertyRepository.findAll();
        List<PropertyResponse> responseList = new ArrayList<>();

        for (Property property : properties) {
            responseList.add(toResponse(property));
        }

        return responseList;
    }

    // 3. Get a single property by ID
    public PropertyResponse getPropertyById(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found"));

        return toResponse(property);
    }

    // 4. Search properties using simple if conditions
    public PropertyPageResponse searchProperties(
            String city,
            String locality,
            Long minPrice,
            Long maxPrice,
            BigDecimal minAreaSqft,
            BigDecimal maxAreaSqft,
            PropertyType propertyType,
            String bhk,
            AvailabilityStatus availabilityStatus,
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        List<Property> allProperties = propertyRepository.findAll();
        List<Property> filteredProperties = new ArrayList<>();

        for (Property property : allProperties) {
            // If city does not match -> skip
            if (city != null && !city.isBlank()) {
                if (property.getCity() == null || !property.getCity().equalsIgnoreCase(city)) {
                    continue;
                }
            }

            // If locality does not match -> skip
            if (locality != null && !locality.isBlank()) {
                if (property.getLocality() == null || !property.getLocality().equalsIgnoreCase(locality)) {
                    continue;
                }
            }

            // If price is too low -> skip
            if (minPrice != null) {
                if (property.getPriceInr() == null || property.getPriceInr() < minPrice) {
                    continue;
                }
            }

            // If price is too high -> skip
            if (maxPrice != null) {
                if (property.getPriceInr() == null || property.getPriceInr() > maxPrice) {
                    continue;
                }
            }

            // If area is too small -> skip
            if (minAreaSqft != null) {
                if (property.getAreaSqft() == null || property.getAreaSqft().compareTo(minAreaSqft) < 0) {
                    continue;
                }
            }

            // If area is too large -> skip
            if (maxAreaSqft != null) {
                if (property.getAreaSqft() == null || property.getAreaSqft().compareTo(maxAreaSqft) > 0) {
                    continue;
                }
            }

            // If property type does not match -> skip
            if (propertyType != null) {
                if (property.getPropertyType() != propertyType) {
                    continue;
                }
            }

            // If BHK does not match -> skip
            if (bhk != null && !bhk.isBlank()) {
                if (property.getBhk() == null || !property.getBhk().equalsIgnoreCase(bhk)) {
                    continue;
                }
            }

            // If availability status does not match -> skip
            if (availabilityStatus != null) {
                if (property.getAvailabilityStatus() != availabilityStatus) {
                    continue;
                }
            }

            // If property passed all checks, keep it
            filteredProperties.add(property);
        }

        // Simple sorting
        filteredProperties.sort((p1, p2) -> {
            int comparison;
            if ("price".equalsIgnoreCase(sortBy)) {
                Long val1 = p1.getPriceInr() != null ? p1.getPriceInr() : 0L;
                Long val2 = p2.getPriceInr() != null ? p2.getPriceInr() : 0L;
                comparison = val1.compareTo(val2);
            } else if ("area".equalsIgnoreCase(sortBy)) {
                BigDecimal val1 = p1.getAreaSqft() != null ? p1.getAreaSqft() : BigDecimal.ZERO;
                BigDecimal val2 = p2.getAreaSqft() != null ? p2.getAreaSqft() : BigDecimal.ZERO;
                comparison = val1.compareTo(val2);
            } else if ("checked".equalsIgnoreCase(sortBy)) {
                LocalDateTime val1 = p1.getLastCheckedAt() != null ? p1.getLastCheckedAt() : LocalDateTime.MIN;
                LocalDateTime val2 = p2.getLastCheckedAt() != null ? p2.getLastCheckedAt() : LocalDateTime.MIN;
                comparison = val1.compareTo(val2);
            } else {
                LocalDateTime val1 = p1.getCreatedAt() != null ? p1.getCreatedAt() : LocalDateTime.MIN;
                LocalDateTime val2 = p2.getCreatedAt() != null ? p2.getCreatedAt() : LocalDateTime.MIN;
                comparison = val1.compareTo(val2);
            }

            return "asc".equalsIgnoreCase(sortDirection) ? comparison : -comparison;
        });

        // Simple pagination
        int totalProperties = filteredProperties.size();
        int totalPages = (int) Math.ceil((double) totalProperties / size);
        if (totalPages == 0) {
            totalPages = 1;
        }

        int fromIndex = page * size;
        List<PropertyResponse> pageContent = new ArrayList<>();

        if (fromIndex < totalProperties) {
            int toIndex = Math.min(fromIndex + size, totalProperties);
            for (int i = fromIndex; i < toIndex; i++) {
                pageContent.add(toResponse(filteredProperties.get(i)));
            }
        }

        boolean isLast = (page + 1) >= totalPages;

        return new PropertyPageResponse(
                pageContent,
                page,
                size,
                totalProperties,
                totalPages,
                isLast
        );
    }

    // Helper: Convert Property entity to PropertyResponse DTO
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
