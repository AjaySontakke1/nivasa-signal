package com.nivasasignal.user.dto;

public record LoginUserResponse(
        Long id,
        String fullName,
        String email,
        boolean admin
) {
}
