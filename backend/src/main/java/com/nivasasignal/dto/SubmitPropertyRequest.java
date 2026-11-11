package com.nivasasignal.dto;

import com.nivasasignal.enums.PropertyType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record SubmitPropertyRequest(

        @NotBlank(message = "Title is required")
        String title,

        @NotNull(message = "Property type is required")
        PropertyType propertyType,

        String bhk,

        @NotBlank(message = "City is required")
        String city,

        @NotBlank(message = "Locality is required")
        String locality,

        String address,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be greater than zero")
        Long priceInr,

        @NotNull(message = "Area is required")
        @DecimalMin(value = "1.0", message = "Area must be greater than zero")
        BigDecimal areaSqft
) {
}
