package de.lhind.internship.mini.project.service;

import de.lhind.internship.mini.project.dto.ReservationDTO;
import de.lhind.internship.mini.project.entity.Guest;
import de.lhind.internship.mini.project.entity.Room;
import de.lhind.internship.mini.project.entity.RoomStatus;
import de.lhind.internship.mini.project.exception.RoomNotAvailableException;
import de.lhind.internship.mini.project.repository.GuestRepository;
import de.lhind.internship.mini.project.repository.ReservationRepository;
import de.lhind.internship.mini.project.repository.RoomRepository;
import de.lhind.internship.mini.project.util.LogTestHelper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class ReservationServiceLoggingTest {

    private static final String LOGGER_NAME = "de.lhind.internship.mini.project.service.ReservationService";

    @Test
    void createReservation_roomUnderMaintenance_logsError() {
        ReservationRepository reservationRepo = Mockito.mock(ReservationRepository.class);
        RoomRepository roomRepo = Mockito.mock(RoomRepository.class);
        GuestRepository guestRepo = Mockito.mock(GuestRepository.class);
        ReservationService service = new ReservationService(reservationRepo, roomRepo, guestRepo);

        Guest guest = new Guest();
        guest.setId(1);
        Room room = new Room();
        room.setId(1);
        room.setCapacity(2);
        room.setStatus(RoomStatus.MAINTENANCE);

        when(guestRepo.findById(1)).thenReturn(Optional.of(guest));
        when(roomRepo.findById(1)).thenReturn(Optional.of(room));

        ReservationDTO dto = ReservationDTO.builder()
                .guestId(1)
                .roomId(1)
                .checkInDate(LocalDate.now())
                .checkOutDate(LocalDate.now().plusDays(2))
                .numberOfGuests(1)
                .build();

        List<LogEvent> events = LogTestHelper.capture(LOGGER_NAME, Level.TRACE,
                () -> assertThrows(RoomNotAvailableException.class, () -> service.createReservation(dto)));

        assertTrue(events.stream().anyMatch(e -> e.getLevel() == Level.ERROR));
    }
}
