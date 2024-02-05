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
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String city;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
