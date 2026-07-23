package com.lhind.service;

import de.lhind.internship.mini.project.dto.GuestDTO;
import de.lhind.internship.mini.project.entity.Guest;
import de.lhind.internship.mini.project.repository.GuestRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GuestService {
    private GuestRepository guestRepository;

    private GuestDTO toDTO(Guest guest) {
        return GuestDTO.builder()
                .id(guest.getId())
                .firstName(guest.getFirstName())
                .lastName(guest.getLastName())
                .email(guest.getEmail())
                .phoneNumber(guest.getPhoneNumber())
                .build();
    }

    private Guest findGuestOrThrow(int id) {
        return guestRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Guest not found with id " + id));
    }

    public GuestDTO createGuest(GuestDTO guestDTO) {
        Guest existing = guestRepository.findFirstByEmail(guestDTO.getEmail());
        if (existing != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "A guest with email " + guestDTO.getEmail() + " already exists");
        }

        Guest guest = new Guest();
        guest.setFirstName(guestDTO.getFirstName());
        guest.setLastName(guestDTO.getLastName());
        guest.setEmail(guestDTO.getEmail());
        guest.setPhoneNumber(guestDTO.getPhoneNumber());

        Guest saved = guestRepository.save(guest);
        return toDTO(saved);
    }

    public List<GuestDTO> getAllGuests() {
        return guestRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public GuestDTO getGuest(int id) {
        return toDTO(findGuestOrThrow(id));
    }

    public GuestDTO updateGuest(int id, GuestDTO guestDTO) {
        Guest guest = findGuestOrThrow(id);

        Guest existing = guestRepository.findFirstByEmail(guestDTO.getEmail());
        if (existing != null && existing.getId() != id) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "A guest with email " + guestDTO.getEmail() + " already exists");
        }

        guest.setFirstName(guestDTO.getFirstName());
        guest.setLastName(guestDTO.getLastName());
        guest.setEmail(guestDTO.getEmail());
        guest.setPhoneNumber(guestDTO.getPhoneNumber());

        Guest saved = guestRepository.save(guest);
        return toDTO(saved);
    }
}
