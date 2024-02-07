package com.mertkaanguzel.flightsearch.controller;

import com.mertkaanguzel.flightsearch.dto.AirportDto;
import com.mertkaanguzel.flightsearch.dto.CreateUpdateAirportDto;
import com.mertkaanguzel.flightsearch.dto.CreateUpdateFlightDto;
import com.mertkaanguzel.flightsearch.dto.FlightDto;
import com.mertkaanguzel.flightsearch.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {
    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<FlightDto> createFlight(@Valid @RequestBody CreateUpdateFlightDto flightDto) {
        return ResponseEntity.ok(flightService.createFlight(flightDto));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<FlightDto> getFlightById(@PathVariable String id) {
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<FlightDto>> getFlights(@RequestParam(value = "origin", required = true) String origin,
                                                      @RequestParam(value = "destination", required = true) String destination,
                                                      @RequestParam(value = "departureDate", required = true) String departureDate,
                                                      @RequestParam(value = "returnDate", required = false) String returnDate,
                                                      @RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit,
                                                      @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {

        return ResponseEntity.ok(flightService.getFlights(origin, destination,
                departureDate, returnDate, limit, offset));
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<FlightDto> updateFlight(@PathVariable String id, @RequestBody CreateUpdateFlightDto flightDto) {
        return ResponseEntity.ok(flightService.updateAirport(id, flightDto));
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFlight(@PathVariable String id) {
        flightService.deleteFlight(id);
    }

    @GetMapping("/mock")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<FlightDto> getMockFlight() {
        return ResponseEntity.ok(new FlightDto("", new AirportDto("", "Ankara"),
                new AirportDto("", "Istanbul"),
                LocalDate.ofInstant(flightService.getInstant(), ZoneId.systemDefault()), 0L));
    }
}
