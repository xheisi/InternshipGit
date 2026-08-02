package de.lhind.internship.mini.project.service;

import de.lhind.internship.mini.project.dto.GuestProfileDTO;
import de.lhind.internship.mini.project.entity.Guest;
import de.lhind.internship.mini.project.entity.GuestProfile;
import de.lhind.internship.mini.project.repository.GuestProfileRepository;
import de.lhind.internship.mini.project.repository.GuestRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class GuestProfileService {

    private static final Logger log = LogManager.getLogger(GuestProfileService.class);

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
        log.trace("Entering findGuestOrThrow() — guestId={}", guestId);
        return guestRepository.findById(guestId)
                .orElseThrow(() -> {
                    log.warn("Guest not found — guestId={}", guestId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Guest not found with id " + guestId);
                });
    }

    public GuestProfileDTO createProfile(int guestId, GuestProfileDTO profileDTO) {
        log.trace("Entering createProfile() — guestId={}", guestId);
        Guest guest = findGuestOrThrow(guestId);

        log.debug("Saving profile for guestId={} with data: {}", guestId, profileDTO);
        GuestProfile profile = guestProfileRepository.findByGuestId(guestId).orElseGet(GuestProfile::new);
        profile.setGuest(guest);
        profile.setAddress(profileDTO.getAddress());
        profile.setDateOfBirth(profileDTO.getDateOfBirth());
        profile.setNationality(profileDTO.getNationality());
        profile.setPreferredLanguage(profileDTO.getPreferredLanguage());

        GuestProfile saved = guestProfileRepository.save(profile);
        log.info("Guest profile saved successfully — guestId={}", guestId);
        return toDTO(saved);
    }

    public GuestProfileDTO getProfile(int guestId) {
        log.trace("Entering getProfile() — guestId={}", guestId);
        findGuestOrThrow(guestId);
        GuestProfile profile = guestProfileRepository.findByGuestId(guestId)
                .orElseThrow(() -> {
                    log.warn("No profile found for guestId={}", guestId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "No profile found for guest with id " + guestId);
                });
        log.info("Guest profile retrieved — guestId={}", guestId);
        return toDTO(profile);
    }
}
