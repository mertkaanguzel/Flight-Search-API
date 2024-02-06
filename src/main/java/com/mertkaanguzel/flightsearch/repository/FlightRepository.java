package com.mertkaanguzel.flightsearch.repository;

import com.mertkaanguzel.flightsearch.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
}
