package com.nivasasignal.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SourceListingResponse(
        Long id,
        Long propertyId,
        String sourceName,
        String externalListingId,
        String sourceUrl,
        String sourcePropertyType,
        String sourceBhk,
        BigDecimal sourceAreaSqft,
        Long sourcePriceInr,
        String sourceStatus,
        LocalDateTime lastSeenAt
) {
}
