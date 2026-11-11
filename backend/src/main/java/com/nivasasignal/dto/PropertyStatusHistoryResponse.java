package com.nivasasignal.dto;

import java.time.LocalDateTime;

public record PropertyStatusHistoryResponse(
        Long id,
        Long propertyId,
        String availabilityStatus,
        LocalDateTime changedAt
) {
}
