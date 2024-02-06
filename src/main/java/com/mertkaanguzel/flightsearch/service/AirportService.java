package com.mertkaanguzel.flightsearch.service;

import com.mertkaanguzel.flightsearch.dto.AirportDto;
import com.mertkaanguzel.flightsearch.dto.CreateUpdateAirportDto;
import com.mertkaanguzel.flightsearch.exception.ResourceAlreadyExistsException;
import com.mertkaanguzel.flightsearch.exception.ResourceNotFoundException;
import com.mertkaanguzel.flightsearch.model.Airport;
import com.mertkaanguzel.flightsearch.repository.AirportRepository;
import org.springframework.stereotype.Service;

import java.time.Clock;

@Service
public class AirportService {
    private final AirportRepository airportRepository;
    private final Clock clock;

    public AirportService(AirportRepository airportRepository, Clock clock) {
        this.airportRepository = airportRepository;
        this.clock = clock;
    }

    public AirportDto createAirport(CreateUpdateAirportDto airportDto) {
        if (airportRepository.findByCity(airportDto.city()).isPresent()) {
            throw new ResourceAlreadyExistsException("City already exists");
        }

        Airport airport = Airport.builder()
                .city(airportDto.city())
                .build();

        airport = airportRepository.saveAndFlush(airport);

        return new AirportDto(String.valueOf(airport.id()), airport.city());
    }

    public Airport findAirportById(String id) {
        return airportRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found"));
    }

    public AirportDto getAirportById(String id) {
        Airport airport = findAirportById(id);
        return new AirportDto(String.valueOf(airport.id()), airport.city());
    }
    public AirportDto updateAirport(String id, CreateUpdateAirportDto airportDto) {
        Airport airport = findAirportById(id);
        airport.city(airportDto.city());

        airport = airportRepository.save(airport);

        return new AirportDto(String.valueOf(airport.id()), airport.city());
    }

    public void deleteAirport(String id) {
        Airport airport = findAirportById(id);
        airportRepository.delete(airport);
    }
}
