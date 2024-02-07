package com.mertkaanguzel.flightsearch.service;

import com.mertkaanguzel.flightsearch.config.OffsetBasedPageRequest;
import com.mertkaanguzel.flightsearch.dto.AirportDto;
import com.mertkaanguzel.flightsearch.dto.CreateUpdateAirportDto;
import com.mertkaanguzel.flightsearch.dto.CreateUpdateFlightDto;
import com.mertkaanguzel.flightsearch.dto.FlightDto;
import com.mertkaanguzel.flightsearch.exception.ResourceAlreadyExistsException;
import com.mertkaanguzel.flightsearch.exception.ResourceNotFoundException;
import com.mertkaanguzel.flightsearch.model.Airport;
import com.mertkaanguzel.flightsearch.model.Flight;
import com.mertkaanguzel.flightsearch.repository.FlightRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Service
public class FlightService {
    private final FlightRepository flightRepository;
    private final AirportService airportService;
    private final Clock clock;

    public FlightService(FlightRepository flightRepository, AirportService airportService, Clock clock) {
        this.flightRepository = flightRepository;
        this.airportService = airportService;
        this.clock = clock;
    }
    public FlightDto createFlight(CreateUpdateFlightDto flightDto) {
        if (!flightRepository.findByOriginAirportCityAndDestinationAirportCityAndDepartureDateAndPrice(
                flightDto.origin(),
                flightDto.destination(),
                LocalDate.parse(flightDto.departureDate()),
                Long.parseLong(flightDto.price())
        ).isEmpty()) {
            throw new ResourceAlreadyExistsException("Flight already exists");
        }

        Airport originAirport = airportService.findAirportByCity(flightDto.origin());
        Airport destinationAirport = airportService.findAirportByCity(flightDto.destination());

        Flight flight = Flight.builder()
                .originAirport(originAirport)
                .destinationAirport(destinationAirport)
                .departureDate(LocalDate.parse(flightDto.departureDate()))
                .price(Long.parseLong(flightDto.price()))
                .build();

        flight = flightRepository.saveAndFlush(flight);

        return FlightDto.fromFlight(flight);
    }

    public FlightDto updateAirport(String id, CreateUpdateFlightDto flightDto) {
        Flight flight = findFlightById(id);

        if (flightDto.origin() != null) {
            Airport originAirport = airportService.findAirportByCity(flightDto.origin());
            flight.originAirport(originAirport);
        }
        if (flightDto.destination() != null) {
            Airport destinationAirport = airportService.findAirportByCity(flightDto.destination());
            flight.destinationAirport(destinationAirport);
        }
        if (flightDto.departureDate() != null) flight.departureDate(LocalDate.parse(flightDto.departureDate()));
        if (flightDto.price() != null) flight.price(Long.valueOf(flightDto.price()));

        flight = flightRepository.save(flight);

        return FlightDto.fromFlight(flight);
    }

    public List<FlightDto> getFlights(String origin, String destination, String departureDate,
                                       String returnDate, Integer limit, Integer offset) {
        Pageable pageable = new OffsetBasedPageRequest(offset, limit);
        List<Flight> flights;

        if (returnDate == null) {
            flights =  flightRepository.findFlights(origin, destination, LocalDate.parse(departureDate), pageable);
        } else {
            flights =  flightRepository.findRoundtripFlights(origin, destination, LocalDate.parse(departureDate),
                    LocalDate.parse(returnDate), pageable);
        }

        return flights.stream().map(FlightDto::fromFlight).toList();
    }

    public Flight findFlightById(String id) {
        return flightRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));
    }

    public FlightDto getFlightById(String id) {
        Flight flight = findFlightById(id);
        return FlightDto.fromFlight(flight);
    }

    public void deleteFlight(String id) {
        Flight flight = findFlightById(id);
        flightRepository.delete(flight);
    }

    private Instant getInstant() {
        return clock.instant();
    }
}
