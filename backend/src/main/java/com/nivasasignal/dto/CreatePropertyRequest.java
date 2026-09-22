package com.nivasasignal.dto;

import com.nivasasignal.enums.AvailabilityStatus;
import com.nivasasignal.enums.PropertyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreatePropertyRequest(

        @NotBlank(message = "Title is required")
        @Size(max = 255, message = "Title must be under 255 characters")
        String title,

        @NotNull(message = "Property type is required")
        PropertyType propertyType,

        @Size(max = 20, message = "BHK must be under 20 characters")
        String bhk,

        @NotBlank(message = "City is required")
        @Size(max = 100)
        String city,

        @Size(max = 150)
        String locality,

        @Size(max = 500)
        String address,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be greater than zero")
        Long priceInr,

        @NotNull(message = "Area is required")
        @Positive(message = "Area must be greater than zero")
        BigDecimal areaSqft,

        @NotNull(message = "Availability status is required")
        AvailabilityStatus availabilityStatus
) {
}
