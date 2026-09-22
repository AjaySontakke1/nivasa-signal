package com.nivasasignal.dto;

import java.util.List;

public record PropertyPageResponse(
        List<PropertyResponse> properties,
        int page,
        int size,
        long totalProperties,
        int totalPages,
        boolean last
) {
}
