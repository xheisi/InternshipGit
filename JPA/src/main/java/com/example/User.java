package com.example;
import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter @Setter

public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String username;
    private String password;
    private String role;

    //•	Tables user and user_details should have a One to One relationship.
    @OneToOne(mappedBy = "user")
    private UserDetails userDetails;

    //•	Tables user and booking should have a One to Many relationship.
    @OneToMany(mappedBy = "user")
    private List<Booking> bookings;

}
