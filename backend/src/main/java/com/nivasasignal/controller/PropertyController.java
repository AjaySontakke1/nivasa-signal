package com.nivasasignal.controller;

import com.nivasasignal.dto.CreatePropertyRequest;
import com.nivasasignal.dto.PropertyPageResponse;
import com.nivasasignal.dto.PropertyResponse;
import com.nivasasignal.enums.AvailabilityStatus;
import com.nivasasignal.enums.PropertyType;
import com.nivasasignal.service.PropertyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping
    public ResponseEntity<PropertyResponse> createProperty(
            @Valid @RequestBody CreatePropertyRequest request
    ) {
        PropertyResponse response = propertyService.createProperty(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public List<PropertyResponse> getAllProperties() {
        return propertyService.getAllProperties();
    }

    @GetMapping("/search")
    public PropertyPageResponse searchProperties(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String locality,
            @RequestParam(required = false) @PositiveOrZero Long minPrice,
            @RequestParam(required = false) @PositiveOrZero Long maxPrice,
            @RequestParam(required = false) @PositiveOrZero BigDecimal minAreaSqft,
            @RequestParam(required = false) @PositiveOrZero BigDecimal maxAreaSqft,
            @RequestParam(required = false) PropertyType propertyType,
            @RequestParam(required = false) String bhk,
            @RequestParam(required = false) AvailabilityStatus availabilityStatus,

            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(50) int size,
            @RequestParam(defaultValue = "created") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Minimum price cannot be greater than maximum price"
            );
        }

        if (minAreaSqft != null && maxAreaSqft != null
                && minAreaSqft.compareTo(maxAreaSqft) > 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Minimum area cannot be greater than maximum area"
            );
        }

        return propertyService.searchProperties(
                city,
                locality,
                minPrice,
                maxPrice,
                minAreaSqft,
                maxAreaSqft,
                propertyType,
                bhk,
                availabilityStatus,
                page,
                size,
                sortBy,
                sortDirection
        );
    }

    @GetMapping("/{id}")
    public PropertyResponse getPropertyById(@PathVariable Long id) {
        return propertyService.getPropertyById(id);
    }
}
