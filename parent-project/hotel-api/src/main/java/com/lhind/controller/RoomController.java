package com.lhind.controller;

import de.lhind.internship.mini.project.dto.RoomDTO;
import de.lhind.internship.mini.project.entity.RoomStatus;
import de.lhind.internship.mini.project.service.RoomService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class RoomController {
    private RoomService roomService;

    @PostMapping("/api/hotels/{hotelId}/rooms")
    public ResponseEntity<RoomDTO> addRoom(@PathVariable int hotelId, @Valid @RequestBody RoomDTO roomDTO) {
        RoomDTO created = roomService.createRoom(hotelId, roomDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/api/hotels/{hotelId}/rooms")
    public ResponseEntity<List<RoomDTO>> getRoomsForHotel(@PathVariable int hotelId) {
        return new ResponseEntity<>(roomService.getRoomsForHotel(hotelId), HttpStatus.OK);
    }

    @GetMapping("/api/rooms/{id}")
    public ResponseEntity<RoomDTO> getRoom(@PathVariable int id) {
        return new ResponseEntity<>(roomService.getRoom(id), HttpStatus.OK);
    }

    @PutMapping("/api/rooms/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable int id, @Valid @RequestBody RoomDTO roomDTO) {
        return new ResponseEntity<>(roomService.updateRoom(id, roomDTO), HttpStatus.OK);
    }

    @PatchMapping("/api/rooms/{id}/status")
    public ResponseEntity<RoomDTO> updateRoomStatus(@PathVariable int id, @RequestBody Map<String, String> body) {
        RoomStatus status = RoomStatus.valueOf(body.get("status").toUpperCase());
        return new ResponseEntity<>(roomService.updateRoomStatus(id, status), HttpStatus.OK);
    }

    @DeleteMapping("/api/rooms/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable int id) {
        roomService.deleteRoom(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/api/rooms/search")
    public ResponseEntity<List<RoomDTO>> searchRooms(@RequestParam int hotelId, @RequestParam RoomStatus status) {
        return new ResponseEntity<>(roomService.searchRooms(hotelId, status), HttpStatus.OK);
    }
}
