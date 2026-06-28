package com.example;
import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "booking")

@Getter
@Setter

public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private  String status;
    private String bookingDate;

    //•	Tables user and booking should have a One to Many relationship.
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //•	Tables booking and flight should have a Many to Many relationship.
    @ManyToMany
    @JoinTable(
            name = "booking_flight",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "flight_id")
    )
    private List<Flight> flights;
}
