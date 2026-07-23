package com.lhind.repository;

import de.lhind.internship.mini.project.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    //Method-name queries Implement at least two:
    // findByCityIgnoreCase

    List<Hotel> findByCityIgnoreCase(String city);
}
