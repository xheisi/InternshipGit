package com.example;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "booking")
@Getter @Setter

public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long flightId;
    private String origin;
    private String destination;
    private String airline;
    private String flightNumber;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private String status;
    private Long userId;

    @ManyToOne
    @JoinColumn(name="userId", nullable=false)
    private User user;

}
