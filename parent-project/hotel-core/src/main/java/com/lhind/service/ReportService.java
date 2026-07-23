package com.lhind.service;

import de.lhind.internship.mini.project.dto.MostReservedRoomDTO;
import de.lhind.internship.mini.project.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReportService {
    private ReservationRepository reservationRepository;

    public List<MostReservedRoomDTO> getMostReservedRooms() {
        return reservationRepository.findMostReservedRooms().stream()
                .map(row -> MostReservedRoomDTO.builder()
                        .roomId(((Number) row[0]).intValue())
                        .roomNumber((String) row[1])
                        .hotelId(((Number) row[2]).intValue())
                        .reservationCount(((Number) row[3]).longValue())
                        .build())
                .collect(Collectors.toList());
    }
}
