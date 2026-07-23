package com.lhind.service;

import de.lhind.internship.mini.project.dto.RoomDTO;
import de.lhind.internship.mini.project.entity.Hotel;
import de.lhind.internship.mini.project.entity.Room;
import de.lhind.internship.mini.project.entity.RoomStatus;
import de.lhind.internship.mini.project.repository.HotelRepository;
import de.lhind.internship.mini.project.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoomService {
    private RoomRepository roomRepository;
    private HotelRepository hotelRepository;

    private RoomDTO toDTO(Room room) {
        return RoomDTO.builder()
                .id(room.getId())
                .hotelId(room.getHotel().getId())
                .roomNumber(room.getRoomNumber())
                .roomType(room.getRoomType())
                .capacity(room.getCapacity())
                .pricePerNight(room.getPricePerNight())
                .status(room.getStatus())
                .build();
    }

    private Hotel findHotelOrThrow(int hotelId) {
        return hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found with id " + hotelId));
    }

    private Room findRoomOrThrow(int id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found with id " + id));
    }

    public RoomDTO createRoom(int hotelId, RoomDTO roomDTO) {
        Hotel hotel = findHotelOrThrow(hotelId);

        Room room = new Room();
        room.setHotel(hotel);
        room.setRoomNumber(roomDTO.getRoomNumber());
        room.setRoomType(roomDTO.getRoomType());
        room.setCapacity(roomDTO.getCapacity());
        room.setPricePerNight(roomDTO.getPricePerNight());
        room.setStatus(roomDTO.getStatus() != null ? roomDTO.getStatus() : RoomStatus.AVAILABLE);

        Room saved = roomRepository.save(room);
        return toDTO(saved);
    }

    public List<RoomDTO> getRoomsForHotel(int hotelId) {
        findHotelOrThrow(hotelId);
        return roomRepository.findByHotelId(hotelId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public RoomDTO getRoom(int id) {
        return toDTO(findRoomOrThrow(id));
    }

    public RoomDTO updateRoom(int id, RoomDTO roomDTO) {
        Room room = findRoomOrThrow(id);
        room.setRoomNumber(roomDTO.getRoomNumber());
        room.setRoomType(roomDTO.getRoomType());
        room.setCapacity(roomDTO.getCapacity());
        room.setPricePerNight(roomDTO.getPricePerNight());
        if (roomDTO.getStatus() != null) {
            room.setStatus(roomDTO.getStatus());
        }
        Room saved = roomRepository.save(room);
        return toDTO(saved);
    }

    public RoomDTO updateRoomStatus(int id, RoomStatus status) {
        Room room = findRoomOrThrow(id);
        room.setStatus(status);
        Room saved = roomRepository.save(room);
        return toDTO(saved);
    }

    public void deleteRoom(int id) {
        Room room = findRoomOrThrow(id);
        roomRepository.delete(room);
    }

    public List<RoomDTO> searchRooms(int hotelId, RoomStatus status) {
        return roomRepository.findByHotelIdAndStatus(hotelId, status).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
