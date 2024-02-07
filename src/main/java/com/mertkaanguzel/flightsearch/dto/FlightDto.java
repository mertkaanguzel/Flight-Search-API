package com.mertkaanguzel.flightsearch.dto;

import com.mertkaanguzel.flightsearch.model.Flight;

import java.time.LocalDate;

import static com.mertkaanguzel.flightsearch.dto.AirportDto.fromAirport;

public record FlightDto(
        String id,
        AirportDto originAirport,
        AirportDto destinationAirport,
        LocalDate departureDate,
        Long price
) {
    public static FlightDto fromFlight(Flight flight) {
        return new FlightDto(
          String.valueOf(flight.id()),
          fromAirport(flight.originAirport()),
          fromAirport(flight.destinationAirport()),
          flight.departureDate(),
          flight.price()
        );
    }
}
