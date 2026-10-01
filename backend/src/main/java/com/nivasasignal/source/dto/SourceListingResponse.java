package com.nivasasignal.source.dto;

import java.time.LocalDateTime;

public record SourceListingResponse(
        Long id,
        Long propertyId,
        String sourceName,
        String externalListingId,
        String sourceUrl,
        Long sourcePriceInr,
        String sourceStatus,
        LocalDateTime lastSeenAt
) {
}
