package com.nivasasignal.dto;

import java.util.List;

public record PropertyDetailsResponse(
        PropertyResponse property,
        List<SourceListingResponse> sourceListings
) {
}
