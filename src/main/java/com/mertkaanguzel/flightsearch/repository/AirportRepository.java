package com.mertkaanguzel.flightsearch.repository;

import com.mertkaanguzel.flightsearch.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, Long> {
}
