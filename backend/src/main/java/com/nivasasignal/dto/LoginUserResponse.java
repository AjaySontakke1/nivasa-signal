package com.nivasasignal.dto;

public record LoginUserResponse(
        Long id,
        String fullName,
        String email,
        boolean admin,
        String token
) {
}
