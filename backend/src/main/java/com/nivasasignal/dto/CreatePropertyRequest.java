package com.nivasasignal.dto;

import com.nivasasignal.enums.AvailabilityStatus;
import com.nivasasignal.enums.PropertyType;

import java.math.BigDecimal;

public record CreatePropertyRequest(
        String title,
        PropertyType propertyType,
        String bhk,
        String city,
        String locality,
        String address,
        Long priceInr,
        BigDecimal areaSqft,
        AvailabilityStatus availabilityStatus
) {
}
