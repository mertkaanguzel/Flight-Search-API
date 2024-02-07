package com.mertkaanguzel.flightsearch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.FutureOrPresent;


public record CreateUpdateFlightDto(
        @NotBlank(message = "Origin must not be empty")
        String origin,
        @NotBlank(message = "Destination must not be empty")
        String destination,
        @NotBlank(message = "Departure date must not be empty")
        String departureDate,
        @NotBlank(message = "Price must not be empty")
        String price
) {
}
