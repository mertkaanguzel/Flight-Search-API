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
            throw new ResourceAlreadyExistsException("Airport already exists");
        }

        Airport airport = Airport.builder()
                .city(airportDto.city())
                .build();

        airport = airportRepository.saveAndFlush(airport);

        return AirportDto.fromAirport(airport);
    }

    public Airport findAirportById(String id) {
        return airportRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found"));
    }

    public Airport findAirportByCity(String city) {
        return airportRepository.findByCity(city)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found"));
    }

    public AirportDto getAirportById(String id) {
        Airport airport = findAirportById(id);
        return AirportDto.fromAirport(airport);
    }
    public AirportDto updateAirport(String id, CreateUpdateAirportDto airportDto) {
        Airport airport = findAirportById(id);
        airport.city(airportDto.city());

        airport = airportRepository.save(airport);

        return AirportDto.fromAirport(airport);
    }

    public void deleteAirport(String id) {
        Airport airport = findAirportById(id);
        airportRepository.delete(airport);
    }
}
