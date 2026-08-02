package de.lhind.internship.mini.project.service;

import de.lhind.internship.mini.project.dto.MostReservedRoomDTO;
import de.lhind.internship.mini.project.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReportService {

    private static final Logger log = LogManager.getLogger(ReportService.class);

    private ReservationRepository reservationRepository;

    public List<MostReservedRoomDTO> getMostReservedRooms() {
        log.trace("Entering getMostReservedRooms()");
        log.debug("Running most-reserved-rooms aggregation query");

        List<MostReservedRoomDTO> result = reservationRepository.findMostReservedRooms().stream()
                .map(row -> MostReservedRoomDTO.builder()
                        .roomId(((Number) row[0]).intValue())
                        .roomNumber((String) row[1])
                        .hotelId(((Number) row[2]).intValue())
                        .reservationCount(((Number) row[3]).longValue())
                        .build())
                .collect(Collectors.toList());

        log.info("Most-reserved-rooms report generated — {} rows returned", result.size());
        return result;
    }
}
