package com.example;
import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "flight")
@Getter @Setter

public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String origin;
    private String destination;
    private String airline;
    private String flightNumber;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private String status;

    @ManyToOne
    @JoinColumn(name="userId", nullable=false)
    private User user;

    //•	Tables booking and flight should have a Many to Many relationship.
    @ManyToMany(mappedBy = "flights")
    private List<Booking> bookings;
}
