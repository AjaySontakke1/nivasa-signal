package com.nivasasignal.service;

import com.nivasasignal.dto.CreatePropertyRequest;
import com.nivasasignal.dto.PropertyPageResponse;
import com.nivasasignal.dto.PropertyResponse;
import com.nivasasignal.entity.Property;
import com.nivasasignal.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    // 3. Get a single property by its ID
    public PropertyResponse getPropertyById(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found"));

        return toResponse(property);
    }

    // 4. Search properties with filters, pagination, and sorting
    public PropertyPageResponse searchProperties(
            String city,
            String locality,
            Long minPrice,
            Long maxPrice,
            BigDecimal minAreaSqft,
            BigDecimal maxAreaSqft,
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        // Choose which database column to sort by
        String sortColumn = "createdAt";
        if ("price".equalsIgnoreCase(sortBy)) {
            sortColumn = "priceInr";
        } else if ("area".equalsIgnoreCase(sortBy)) {
            sortColumn = "areaSqft";
        } else if ("checked".equalsIgnoreCase(sortBy)) {
            sortColumn = "lastCheckedAt";
        }

        // Choose ascending or descending order
        Sort sort = "asc".equalsIgnoreCase(sortDirection)
                ? Sort.by(sortColumn).ascending()
                : Sort.by(sortColumn).descending();

        // Create page request
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        // Fetch properties from repository
        Page<Property> propertyPage = propertyRepository.searchProperties(
                city, locality, minPrice, maxPrice, minAreaSqft, maxAreaSqft, pageRequest
        );

        // Convert entities to response DTO list
        List<PropertyResponse> content = new ArrayList<>();
        for (Property property : propertyPage.getContent()) {
            content.add(toResponse(property));
        }

        return new PropertyPageResponse(
                content,
                propertyPage.getNumber(),
                propertyPage.getSize(),
                propertyPage.getTotalElements(),
                propertyPage.getTotalPages(),
                propertyPage.isLast()
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
