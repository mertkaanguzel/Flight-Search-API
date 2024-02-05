package com.mertkaanguzel.flightsearch.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true) @Getter
@Setter
@Builder
@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "originAirportID", referencedColumnName = "id")
    private Airport originAirport;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "destinationAirportID", referencedColumnName = "id")
    private Airport destinationAirport;

    private LocalDateTime departureDate;

    private LocalDateTime returnDate;

    private Long price;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
