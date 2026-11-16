package com.nivasasignal.dto;

import java.math.BigDecimal;

public record UpdateSourceListingRequest(
        String sourceUrl,
        String sourcePropertyType,
        String sourceBhk,
        BigDecimal sourceAreaSqft,
        Long sourcePriceInr,
        String sourceStatus
) {
}
