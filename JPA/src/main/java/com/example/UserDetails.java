package com.example;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_details")
@Getter
@Setter

public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private int phoneNumber;

    //•	Tables user and user_details should have a One to One relationship
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
