package com.mertkaanguzel.flightsearch.controller;

import com.mertkaanguzel.flightsearch.dto.AirportDto;
import com.mertkaanguzel.flightsearch.dto.CreateUpdateAirportDto;
import com.mertkaanguzel.flightsearch.service.AirportService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    @SecurityRequirement(name = "Flight Search Api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "409", description = "Conflict"),
    })
    public ResponseEntity<AirportDto> createAirport(@Valid @RequestBody CreateUpdateAirportDto airportDto) {
        return ResponseEntity.ok(airportService.createAirport(airportDto));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "Flight Search Api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
    })
    public ResponseEntity<AirportDto> getAirportById(@PathVariable String id) {
        return ResponseEntity.ok(airportService.getAirportById(id));
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "Flight Search Api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
    })
    public ResponseEntity<AirportDto> updateAirport(@PathVariable String id, @Valid @RequestBody CreateUpdateAirportDto airportDto) {
        return ResponseEntity.ok(airportService.updateAirport(id, airportDto));
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "Flight Search Api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
    })
    public void deleteAirport(@PathVariable String id) {
        airportService.deleteAirport(id);
    }
}
