package com.lhind.controller;

import de.lhind.internship.mini.project.dto.MostReservedRoomDTO;
import de.lhind.internship.mini.project.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/reports")
public class ReportController {
    private ReportService reportService;

    @GetMapping("/most-reserved-rooms")
    public ResponseEntity<List<MostReservedRoomDTO>> getMostReservedRooms() {
        return new ResponseEntity<>(reportService.getMostReservedRooms(), HttpStatus.OK);
    }
}
