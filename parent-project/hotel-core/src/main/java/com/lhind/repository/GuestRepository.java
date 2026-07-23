package com.lhind.repository;

import de.lhind.internship.mini.project.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Integer> {

    Guest findFirstByEmail(String email);    //needs to check for duplicate emails

}
