package com.nivasasignal.property;

import com.nivasasignal.property.dto.CreatePropertyRequest;
import com.nivasasignal.property.dto.PropertyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    // POST http://localhost:8080/api/v1/properties
    @PostMapping
    public ResponseEntity<PropertyResponse> createProperty(@RequestBody CreatePropertyRequest request) {
        Property property = new Property();
        property.setTitle(request.getTitle());
        property.setPropertyType(request.getPropertyType());
        property.setBhk(request.getBhk());
        property.setCity(request.getCity());
        property.setLocality(request.getLocality());
        property.setAddress(request.getAddress());
        property.setPriceInr(request.getPriceInr());
        property.setAreaSqft(request.getAreaSqft());
        property.setAvailabilityStatus(request.getAvailabilityStatus());

        Property savedProperty = propertyService.createProperty(property);

        return ResponseEntity.status(HttpStatus.CREATED).body(mapToResponse(savedProperty));
    }

    // GET http://localhost:8080/api/v1/properties
    @GetMapping
    public ResponseEntity<List<PropertyResponse>> getAllProperties() {
        List<Property> properties = propertyService.getAllProperties();
        List<PropertyResponse> responseList = new ArrayList<>();

        for (Property property : properties) {
            responseList.add(mapToResponse(property));
        }

        return ResponseEntity.ok(responseList);
    }

    // GET http://localhost:8080/api/v1/properties/1
    @GetMapping("/{id}")
    public ResponseEntity<PropertyResponse> getPropertyById(@PathVariable Long id) {
        Property property = propertyService.getPropertyById(id);
        if (property == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapToResponse(property));
    }

    // Simple helper method to convert Entity -> Response DTO
    private PropertyResponse mapToResponse(Property property) {
        PropertyResponse response = new PropertyResponse();
        response.setId(property.getId());
        response.setTitle(property.getTitle());
        response.setPropertyType(property.getPropertyType());
        response.setBhk(property.getBhk());
        response.setCity(property.getCity());
        response.setLocality(property.getLocality());
        response.setAddress(property.getAddress());
        response.setPriceInr(property.getPriceInr());
        response.setAreaSqft(property.getAreaSqft());
        response.setAvailabilityStatus(property.getAvailabilityStatus());
        response.setTruthScore(property.getTruthScore());
        response.setLastCheckedAt(property.getLastCheckedAt());
        response.setCreatedAt(property.getCreatedAt());
        response.setUpdatedAt(property.getUpdatedAt());
        return response;
    }
}
