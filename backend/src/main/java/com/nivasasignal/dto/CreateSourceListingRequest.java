package com.nivasasignal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateSourceListingRequest(

        @NotNull
        @Positive
        Long propertyId,

        @NotBlank
        String sourceName,

        @NotBlank
        String externalListingId,

        @NotBlank
        String sourceUrl,

        String sourcePropertyType,

        String sourceBhk,

        BigDecimal sourceAreaSqft,

        @Positive
        Long sourcePriceInr,

        @NotBlank
        String sourceStatus
) {
}
