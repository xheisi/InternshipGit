package com.lhind.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="rooms")
@Getter
@Setter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    private int roomNumber;

    @Enumerated(EnumType.STRING)
    private RoomType roomType = RoomType.STANDARD;

    private int capacity;
    private double pricePerNight;

    @Enumerated(EnumType.STRING)
    private RoomStatus status = RoomStatus.AVAILABLE;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @OneToMany(mappedBy = "room", cascade= CascadeType.ALL, orphanRemoval=true)
    private List<Reservation> reservations = new ArrayList<>();

}
