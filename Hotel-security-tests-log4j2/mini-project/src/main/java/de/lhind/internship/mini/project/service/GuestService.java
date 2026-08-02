package de.lhind.internship.mini.project.service;

import de.lhind.internship.mini.project.dto.GuestDTO;
import de.lhind.internship.mini.project.entity.Guest;
import de.lhind.internship.mini.project.repository.GuestRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GuestService {

    private static final Logger log = LogManager.getLogger(GuestService.class);

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
        log.trace("Entering findGuestOrThrow() — id={}", id);
        return guestRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Guest not found — id={}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Guest not found with id " + id);
                });
    }

    public GuestDTO createGuest(GuestDTO guestDTO) {
        log.trace("Entering createGuest() — email={}", guestDTO.getEmail());
        log.debug("Checking for existing guest with email={}", guestDTO.getEmail());

        Guest existing = guestRepository.findFirstByEmail(guestDTO.getEmail());
        if (existing != null) {
            log.warn("createGuest() rejected — email {} already exists (id={})", guestDTO.getEmail(), existing.getId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "A guest with email " + guestDTO.getEmail() + " already exists");
        }

        Guest guest = new Guest();
        guest.setFirstName(guestDTO.getFirstName());
        guest.setLastName(guestDTO.getLastName());
        guest.setEmail(guestDTO.getEmail());
        guest.setPhoneNumber(guestDTO.getPhoneNumber());

        Guest saved = guestRepository.save(guest);
        log.info("Guest created successfully — id={}, email={}", saved.getId(), saved.getEmail());
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
        log.trace("Entering updateGuest() — id={}", id);
        Guest guest = findGuestOrThrow(id);

        Guest existing = guestRepository.findFirstByEmail(guestDTO.getEmail());
        if (existing != null && existing.getId() != id) {
            log.warn("updateGuest() rejected — email {} already used by another guest (id={})", guestDTO.getEmail(), existing.getId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "A guest with email " + guestDTO.getEmail() + " already exists");
        }

        log.debug("Updating guest id={} with data: {}", id, guestDTO);
        guest.setFirstName(guestDTO.getFirstName());
        guest.setLastName(guestDTO.getLastName());
        guest.setEmail(guestDTO.getEmail());
        guest.setPhoneNumber(guestDTO.getPhoneNumber());

        Guest saved = guestRepository.save(guest);
        log.info("Guest updated successfully — id={}", saved.getId());
        return toDTO(saved);
    }
}
