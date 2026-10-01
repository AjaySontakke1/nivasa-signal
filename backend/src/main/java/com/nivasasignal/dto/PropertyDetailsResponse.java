package com.nivasasignal.dto;

import com.nivasasignal.source.dto.SourceListingResponse;

import java.util.List;

public record PropertyDetailsResponse(
        PropertyResponse property,
        List<SourceListingResponse> sourceListings
) {
}
