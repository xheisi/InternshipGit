package de.lhind.internship.mini.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

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
