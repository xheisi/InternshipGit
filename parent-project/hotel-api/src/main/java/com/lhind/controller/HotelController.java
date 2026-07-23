package com.lhind.controller;

import de.lhind.internship.mini.project.dto.HotelDTO;
import de.lhind.internship.mini.project.service.HotelService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/hotels")
public class HotelController {
    private HotelService hotelService;

    @PostMapping
    public ResponseEntity<HotelDTO> createHotel(@Valid @RequestBody HotelDTO hotelDTO) {
        HotelDTO created = hotelService.createHotel(hotelDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<HotelDTO>> getAllHotels() {
        return new ResponseEntity<>(hotelService.getAllHotels(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDTO> getHotel(@PathVariable int id) {
        return new ResponseEntity<>(hotelService.getHotel(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<HotelDTO>> searchHotelsByCity(@RequestParam String city) {
        return new ResponseEntity<>(hotelService.getHotelsByCity(city), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelDTO> updateHotel(@PathVariable int id, @Valid @RequestBody HotelDTO hotelDTO) {
        return new ResponseEntity<>(hotelService.updateHotel(id, hotelDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable int id) {
        hotelService.deleteHotel(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}