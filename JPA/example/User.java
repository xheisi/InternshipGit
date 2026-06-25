package com.example;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter @Setter

public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long userId;
    private String name;
    private String password;
    private String role;
    private Long flightId;

    @OneToMany(mappedBy = "userId")
    private Set<Flight>  flights;

    @OneToOne
    @JoinColumn(name = "id")
    private UserDetails userDetails;


}
