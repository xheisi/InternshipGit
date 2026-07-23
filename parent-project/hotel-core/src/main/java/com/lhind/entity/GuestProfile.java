package com.lhind.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="guestProfile")
@Getter
@Setter
public class GuestProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    private String address;
    private LocalDate dateOfBirth;
    private String nationality;
    private String preferredLanguage;

    @OneToOne
    @JoinColumn(name="guest_id")
    private Guest guest;
}
