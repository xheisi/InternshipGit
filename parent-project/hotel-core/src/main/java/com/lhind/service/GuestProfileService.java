package com.lhind.service;

import com.lhind.dto.GuestProfileDTO;
import com.lhind.entity.Guest;
import com.lhind.entity.GuestProfile;
import com.lhind.repository.GuestProfileRepository;
import com.lhind.repository.GuestRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class GuestProfileService {
    private GuestProfileRepository guestProfileRepository;
    private GuestRepository guestRepository;

    private GuestProfileDTO toDTO(GuestProfile profile) {
        return GuestProfileDTO.builder()
                .id(profile.getId())
                .address(profile.getAddress())
                .dateOfBirth(profile.getDateOfBirth())
                .nationality(profile.getNationality())
                .preferredLanguage(profile.getPreferredLanguage())
                .build();
    }

    private Guest findGuestOrThrow(int guestId) {
        return guestRepository.findById(guestId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Guest not found with id " + guestId));
    }

    public GuestProfileDTO createProfile(int guestId, GuestProfileDTO profileDTO) {
        Guest guest = findGuestOrThrow(guestId);

        GuestProfile profile = guestProfileRepository.findByGuestId(guestId).orElseGet(GuestProfile::new);
        profile.setGuest(guest);
        profile.setAddress(profileDTO.getAddress());
        profile.setDateOfBirth(profileDTO.getDateOfBirth());
        profile.setNationality(profileDTO.getNationality());
        profile.setPreferredLanguage(profileDTO.getPreferredLanguage());

        GuestProfile saved = guestProfileRepository.save(profile);
        return toDTO(saved);
    }

    public GuestProfileDTO getProfile(int guestId) {
        findGuestOrThrow(guestId);
        GuestProfile profile = guestProfileRepository.findByGuestId(guestId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No profile found for guest with id " + guestId));
        return toDTO(profile);
    }
}
