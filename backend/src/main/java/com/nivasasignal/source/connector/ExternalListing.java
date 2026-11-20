package com.nivasasignal.source.connector;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExternalListing(
        String externalListingId,
        String title,
        String city,
        String locality,
        String sourceUrl,
        String propertyType,
        String bhk,
        BigDecimal areaSqft,
        Long priceInr,
        String status,
        LocalDateTime lastSeenAt
) {
}
