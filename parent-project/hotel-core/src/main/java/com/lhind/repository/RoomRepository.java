package com.lhind.repository;

import de.lhind.internship.mini.project.entity.Room;
import de.lhind.internship.mini.project.entity.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    //Method-name queries Implement at least two:
    // findByHotelIdAndStatus
    List<Room> findByHotelIdAndStatus(int hotelId, RoomStatus status);  //list rooms based on the hotels id and picked status

    List<Room> findByHotelId(int hotelId);      //5./api/hotels/{hotelId}/rooms need to find by hotelID

}
