package com.mertkaanguzel.flightsearch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserDto(
        @NotBlank(message = "Username must not be empty")
        @Size(min = 8, message = "Username length must be greater than 8")
        String username,
        @NotBlank(message = "Password must not be empty")
        @Size(min = 8, message = "Password length must be greater than 8")
        String password) {
}
