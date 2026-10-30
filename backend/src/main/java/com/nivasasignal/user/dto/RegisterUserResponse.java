package com.nivasasignal.user.dto;

import com.nivasasignal.user.UserRole;

import java.time.LocalDateTime;

public record RegisterUserResponse(
        Long id,
        String fullName,
        String email,
        UserRole role,
        boolean emailVerified,
        LocalDateTime createdAt
) {
}
