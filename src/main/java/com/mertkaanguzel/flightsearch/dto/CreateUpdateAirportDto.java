package com.mertkaanguzel.flightsearch.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateUpdateAirportDto(
        @NotBlank(message = "City must not be empty")
        String city
) {
}