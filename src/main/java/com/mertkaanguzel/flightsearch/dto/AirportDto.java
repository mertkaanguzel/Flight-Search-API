package com.mertkaanguzel.flightsearch.dto;

import com.mertkaanguzel.flightsearch.model.Airport;
import com.mertkaanguzel.flightsearch.model.Flight;

public record AirportDto(String id, String city) {
    public static AirportDto fromAirport(Airport airport) {
        return new AirportDto(
                String.valueOf(airport.id()),
                airport.city()
        );
    }
}
