package com.lhind.controller;

import de.lhind.internship.mini.project.dto.GuestDTO;
import de.lhind.internship.mini.project.dto.GuestProfileDTO;
import de.lhind.internship.mini.project.service.GuestProfileService;
import de.lhind.internship.mini.project.service.GuestService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/guests")
public class GuestController {
    private GuestService guestService;
    private GuestProfileService guestProfileService;

    @PostMapping
    public ResponseEntity<GuestDTO> createGuest(@Valid @RequestBody GuestDTO guestDTO) {
        GuestDTO created = guestService.createGuest(guestDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<GuestDTO>> getAllGuests() {
        return new ResponseEntity<>(guestService.getAllGuests(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuestDTO> getGuest(@PathVariable int id) {
        return new ResponseEntity<>(guestService.getGuest(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GuestDTO> updateGuest(@PathVariable int id, @Valid @RequestBody GuestDTO guestDTO) {
        return new ResponseEntity<>(guestService.updateGuest(id, guestDTO), HttpStatus.OK);
    }

    @PostMapping("/{guestId}/profile")
    public ResponseEntity<GuestProfileDTO> createProfile(@PathVariable int guestId, @Valid @RequestBody GuestProfileDTO profileDTO) {
        GuestProfileDTO created = guestProfileService.createProfile(guestId, profileDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{guestId}/profile")
    public ResponseEntity<GuestProfileDTO> getProfile(@PathVariable int guestId) {
        return new ResponseEntity<>(guestProfileService.getProfile(guestId), HttpStatus.OK);
    }
}
