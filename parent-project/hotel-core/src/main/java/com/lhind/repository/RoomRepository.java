package com.lhind.repository;

import com.lhind.entity.Room;
import com.lhind.entity.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    //Method-name queries Implement at least two:
    // findByHotelIdAndStatus
    List<Room> findByHotelIdAndStatus(int hotelId, RoomStatus status);  //list rooms based on the hotels id and picked status

    List<Room> findByHotelId(int hotelId);      //5./api/hotels/{hotelId}/rooms need to find by hotelID

}
