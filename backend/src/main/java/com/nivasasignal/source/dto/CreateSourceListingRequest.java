package com.nivasasignal.source.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateSourceListingRequest(

        @NotNull(message = "Property ID is required")
        @Positive(message = "Property ID must be greater than zero")
        Long propertyId,

        @NotBlank(message = "Source name is required")
        @Size(max = 30)
        String sourceName,

        @NotBlank(message = "External listing ID is required")
        @Size(max = 255)
        String externalListingId,

        @NotBlank(message = "Source URL is required")
        @Size(max = 1000)
        @Pattern(
                regexp = "^https?://.+",
                message = "Source URL must start with http:// or https://"
        )
        String sourceUrl,

        @NotNull(message = "Source price is required")
        @Positive(message = "Source price must be greater than zero")
        Long sourcePriceInr,

        @NotBlank(message = "Source status is required")
        @Size(max = 20)
        String sourceStatus
) {
}
