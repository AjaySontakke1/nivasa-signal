package com.nivasasignal.dto;

import com.nivasasignal.enums.AvailabilityStatus;
import com.nivasasignal.enums.PropertyType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PropertyResponse(
        Long id,
        String title,
        PropertyType propertyType,
        String bhk,
        String city,
        String locality,
        String address,
        Long priceInr,
        BigDecimal areaSqft,
        AvailabilityStatus availabilityStatus,
        Integer truthScore,
        LocalDateTime lastCheckedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
