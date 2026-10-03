package com.nivasasignal.source.dto;

public record UpdateSourceListingRequest(
        String sourceUrl,
        Long sourcePriceInr,
        String sourceStatus
) {
}
