package de.lhind.internship.mini.project.service;

import de.lhind.internship.mini.project.dto.HotelDTO;
import de.lhind.internship.mini.project.entity.Hotel;
import de.lhind.internship.mini.project.repository.HotelRepository;
import jakarta.validation.Valid;
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
public class HotelService {

    private static final Logger log = LogManager.getLogger(HotelService.class);

    private HotelRepository hotelRepository;

    private HotelDTO toDTO(Hotel hotel) {
        return HotelDTO.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .city(hotel.getCity())
                .address(hotel.getAddress())
                .starRating(hotel.getStarRating())
                .build();
    }

    private Hotel findHotelOrThrow(int id) {
        log.trace("Entering findHotelOrThrow() — id={}", id);
        return hotelRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Hotel not found — id={}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found with id " + id);
                });
    }

    public HotelDTO createHotel(HotelDTO hotelDTO) {
        log.trace("Entering createHotel() — name={}", hotelDTO.getName());
        log.debug("Creating hotel with data: {}", hotelDTO);

        Hotel hotel = new Hotel();
        hotel.setName(hotelDTO.getName());
        hotel.setCity(hotelDTO.getCity());
        hotel.setAddress(hotelDTO.getAddress());
        hotel.setStarRating(hotelDTO.getStarRating());
        Hotel saved = hotelRepository.save(hotel);

        log.info("Hotel created successfully — id={}, name='{}'", saved.getId(), saved.getName());
        return toDTO(saved);
    }

    public List<HotelDTO> getHotelsByCity(String city) {
        return hotelRepository.findByCityIgnoreCase(city).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<HotelDTO> getAllHotels() {
        return hotelRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public HotelDTO getHotel(int id) {
        return toDTO(findHotelOrThrow(id));
    }

    public HotelDTO updateHotel(int id, @Valid HotelDTO hotelDTO) {
        log.trace("Entering updateHotel() — id={}", id);
        Hotel hotel = findHotelOrThrow(id);

        log.debug("Updating hotel id={} with data: {}", id, hotelDTO);
        hotel.setName(hotelDTO.getName());
        hotel.setCity(hotelDTO.getCity());
        hotel.setAddress(hotelDTO.getAddress());
        hotel.setStarRating(hotelDTO.getStarRating());
        Hotel saved = hotelRepository.save(hotel);

        log.info("Hotel updated successfully — id={}", saved.getId());
        return toDTO(saved);
    }

    public void deleteHotel(int id) {
        log.trace("Entering deleteHotel() — id={}", id);
        Hotel hotel = findHotelOrThrow(id);
        hotelRepository.delete(hotel);
        log.info("Hotel deleted successfully — id={}", id);
    }
}