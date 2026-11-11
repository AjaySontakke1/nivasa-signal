package com.nivasasignal.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PropertyPassportResponse(
        PropertyResponse property,
        List<SourceListingResponse> sourceListings,
        long activeSourceCount,
        long inactiveSourceCount,
        Long lowestSourcePriceInr,
        Long highestSourcePriceInr,
        Long priceDifferenceInr,
        LocalDateTime lastSourceSeenAt,
        List<String> signals
) {
}
