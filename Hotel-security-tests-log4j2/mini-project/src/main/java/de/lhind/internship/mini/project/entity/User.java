package de.lhind.internship.mini.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

//Staff/admin login account.

@Entity
@Table(name = "app_user")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    // "ROLE_ADMIN" or "ROLE_STAFF" — must be prefixed with ROLE_, Spring Security convention
    @Column(nullable = false)
    private String role;
}
