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
        return propertyRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public PropertyResponse getPropertyById(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Property not found"
                ));

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
        return propertyRepository.searchProperties(
                        city,
                        locality,
                        minPrice,
                        maxPrice,
                        minAreaSqft,
                        maxAreaSqft
                )
                .stream()
                .map(this::toResponse)
                .toList();
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
