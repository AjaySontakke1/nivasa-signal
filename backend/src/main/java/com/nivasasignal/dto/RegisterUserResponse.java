package com.nivasasignal.dto;

import java.time.LocalDateTime;

public record RegisterUserResponse(
        Long id,
        String fullName,
        String email,
        boolean emailVerified,
        LocalDateTime createdAt
) {
}
