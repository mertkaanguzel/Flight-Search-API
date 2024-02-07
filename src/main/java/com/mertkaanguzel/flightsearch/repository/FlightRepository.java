package com.mertkaanguzel.flightsearch.repository;

import com.mertkaanguzel.flightsearch.model.Airport;
import com.mertkaanguzel.flightsearch.model.Flight;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Query("""
                    SELECT f FROM Flight f
                    WHERE ((:origin IS NULL OR f.originAirport.city = :origin)
                    AND (:destination IS NULL OR f.destinationAirport.city = :destination)
                    AND (:departureDate IS NULL OR f.departureDate = :departureDate))
                    OR ((:destination IS NULL OR f.originAirport.city = :destination)
                    AND (:origin IS NULL OR f.destinationAirport.city = :origin)
                    AND (:returnDate IS NULL OR f.departureDate = :returnDate))
                    """)
    List<Flight> findRoundtripFlights(
            @Param("origin") String origin,
            @Param("destination") String destination,
            @Param("departureDate") LocalDate departureDate,
            @Param("returnDate") LocalDate returnDate,
            Pageable pageable
    );

    @Query("""
                    SELECT f FROM Flight f
                    WHERE (:origin IS NULL OR f.originAirport.city = :origin)
                    AND (:destination IS NULL OR f.destinationAirport.city = :destination)
                    AND (:departureDate IS NULL OR f.departureDate = :departureDate)
                    """)
    List<Flight> findFlights(
            @Param("origin") String origin,
            @Param("destination") String destination,
            @Param("departureDate") LocalDate departureDate,
            Pageable pageable
    );
    List<Flight> findByOriginAirportCityAndDestinationAirportCityAndDepartureDateAndPrice(
            String origin,
            String destination,
            LocalDate departureDate,
            Long price
    );

    Optional<Flight> findById(Long id);
}
