package com.mertkaanguzel.flightsearch.controller;

import com.mertkaanguzel.flightsearch.dto.AirportDto;
import com.mertkaanguzel.flightsearch.dto.CreateUpdateAirportDto;
import com.mertkaanguzel.flightsearch.service.AirportService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/airports")
public class AirportController {
    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AirportDto> createAirport(@Valid @RequestBody CreateUpdateAirportDto airportDto) {
        return ResponseEntity.ok(airportService.createAirport(airportDto));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AirportDto> getAirportById(@PathVariable String id) {
        return ResponseEntity.ok(airportService.getAirportById(id));
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AirportDto> updateAirport(@PathVariable String id, @Valid @RequestBody CreateUpdateAirportDto airportDto) {
        return ResponseEntity.ok(airportService.updateAirport(id, airportDto));
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAirport(@PathVariable String id) {
        airportService.deleteAirport(id);
    }
}
