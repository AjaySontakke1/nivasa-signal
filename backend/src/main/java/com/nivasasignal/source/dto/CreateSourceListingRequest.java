package com.nivasasignal.source.dto;

public record CreateSourceListingRequest(
        Long propertyId,
        String sourceName,
        String externalListingId,
        String sourceUrl,
        Long sourcePriceInr,
        String sourceStatus
) {
}
