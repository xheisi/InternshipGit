package de.lhind.internship.mini.project.service;

import de.lhind.internship.mini.project.dto.ReservationDTO;
import de.lhind.internship.mini.project.entity.Guest;
import de.lhind.internship.mini.project.entity.Reservation;
import de.lhind.internship.mini.project.entity.ReservationStatus;
import de.lhind.internship.mini.project.entity.Room;
import de.lhind.internship.mini.project.entity.RoomStatus;
import de.lhind.internship.mini.project.exception.RoomNotAvailableException;
import de.lhind.internship.mini.project.repository.GuestRepository;
import de.lhind.internship.mini.project.repository.ReservationRepository;
import de.lhind.internship.mini.project.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservationService {

    private static final Logger log = LogManager.getLogger(ReservationService.class);

    private ReservationRepository reservationRepository;
    private RoomRepository roomRepository;
    private GuestRepository guestRepository;

    private ReservationDTO toDTO(Reservation reservation) {
        return ReservationDTO.builder()
                .id(reservation.getId())
                .guestId(reservation.getGuest().getId())
                .roomId(reservation.getRoom().getId())
                .checkInDate(reservation.getCheckInDate())
                .checkOutDate(reservation.getCheckOutDate())
                .numberOfGuests(reservation.getNumberOfGuests())
                .totalPrice(reservation.getTotalPrice())
                .status(reservation.getStatus())
                .createdAt(reservation.getCreatedAt())
                .build();
    }

    private Reservation findReservationOrThrow(int id) {
        log.trace("Entering findReservationOrThrow() — id={}", id);
        return reservationRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Reservation not found — id={}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found with id " + id);
                });
    }

    public ReservationDTO createReservation(ReservationDTO dto) {
        log.trace("Entering createReservation() — guestId={}, roomId={}", dto.getGuestId(), dto.getRoomId());

        Guest guest = guestRepository.findById(dto.getGuestId())
                .orElseThrow(() -> {
                    log.warn("createReservation() failed — guest not found, id={}", dto.getGuestId());
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Guest not found with id " + dto.getGuestId());
                });
        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> {
                    log.warn("createReservation() failed — room not found, id={}", dto.getRoomId());
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found with id " + dto.getRoomId());
                });

        log.debug("Validating reservation — dates {} to {}, guests={}", dto.getCheckInDate(), dto.getCheckOutDate(), dto.getNumberOfGuests());

        if (!dto.getCheckOutDate().isAfter(dto.getCheckInDate())) {
            log.warn("createReservation() rejected — checkOutDate not after checkInDate");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "checkOutDate must be after checkInDate");
        }

        if (dto.getNumberOfGuests() > room.getCapacity()) {
            log.warn("createReservation() rejected — roomId={} capacity {} exceeded by {} guests",
                    room.getId(), room.getCapacity(), dto.getNumberOfGuests());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Room " + room.getId() + " has capacity " + room.getCapacity()
                            + " but " + dto.getNumberOfGuests() + " guests were requested");
        }

        if (room.getStatus() == RoomStatus.MAINTENANCE) {
            log.error("createReservation() failed — roomId={} is under MAINTENANCE", room.getId());
            throw new RoomNotAvailableException("Room " + room.getId() + " is under maintenance and cannot be reserved");
        }

        long overlapping = reservationRepository.countOverlappingReservations(
                room.getId(), dto.getCheckInDate(), dto.getCheckOutDate());
        if (overlapping > 0) {
            log.error("createReservation() failed — roomId={} has an overlapping reservation", room.getId());
            throw new RoomNotAvailableException(
                    "Room " + room.getId() + " already has an active reservation overlapping the requested dates");
        }

        long nights = ChronoUnit.DAYS.between(dto.getCheckInDate(), dto.getCheckOutDate());
        double totalPrice = nights * room.getPricePerNight();

        Reservation reservation = new Reservation();
        reservation.setGuest(guest);
        reservation.setRoom(room);
        reservation.setCheckInDate(dto.getCheckInDate());
        reservation.setCheckOutDate(dto.getCheckOutDate());
        reservation.setNumberOfGuests(dto.getNumberOfGuests());
        reservation.setTotalPrice(totalPrice);
        reservation.setStatus(ReservationStatus.PENDING);

        Reservation saved = reservationRepository.save(reservation);
        log.info("Reservation created successfully — id={}, roomId={}, guestId={}, totalPrice={}",
                saved.getId(), room.getId(), guest.getId(), totalPrice);
        return toDTO(saved);
    }

    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ReservationDTO getReservation(int id) {
        return toDTO(findReservationOrThrow(id));
    }

    public ReservationDTO updateStatus(int id, ReservationStatus status) {
        log.trace("Entering updateStatus() — id={}, newStatus={}", id, status);
        Reservation reservation = findReservationOrThrow(id);
        reservation.setStatus(status);
        Reservation saved = reservationRepository.save(reservation);
        log.info("Reservation status updated — id={}, status={}", id, status);
        return toDTO(saved);
    }

    public void cancelReservation(int id) {
        log.trace("Entering cancelReservation() — id={}", id);
        Reservation reservation = findReservationOrThrow(id);
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
        log.info("Reservation cancelled — id={}", id);
    }

    public List<ReservationDTO> getReservationsByGuest(int guestId) {
        guestRepository.findById(guestId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Guest not found with id " + guestId));
        return reservationRepository.findByGuestId(guestId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
