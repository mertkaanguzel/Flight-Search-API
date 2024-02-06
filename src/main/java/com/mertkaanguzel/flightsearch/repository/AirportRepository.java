package com.mertkaanguzel.flightsearch.repository;

import com.mertkaanguzel.flightsearch.model.Airport;
import com.mertkaanguzel.flightsearch.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirportRepository extends JpaRepository<Airport, Long> {
    Optional<Airport> findByCity(String city);
    Optional<Airport> findById(Long id);
}
