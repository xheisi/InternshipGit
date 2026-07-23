package com.lhind.repository;

import de.lhind.internship.mini.project.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    //Method-name queries Implement at least two:
    // findByGuestId.
    List<Reservation> findByGuestId(int guestId);

    //JPQL with @Query
    // Count overlapping active reservations for a room and date range before creating a reservation
    @Query("""
            SELECT COUNT(r)
            FROM Reservation r
            WHERE r.room.id = :roomId
            AND r.status <> de.lhind.internship.mini.project.entity.ReservationStatus.CANCELLED
            AND r.checkInDate < :checkOutDate
            AND r.checkOutDate > :checkInDate
            """)
    long countOverlappingReservations(
            @Param("roomId") int roomId,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate
    );


    //Native SQL Group reservations by room, order by reservation count descending, and return the top five.
    @Query(value = """
            SELECT r.id AS room_id,
                   r.room_number AS room_number,
                   r.hotel_id AS hotel_id,
                   COUNT(res.id) AS reservation_count
            FROM rooms r
            JOIN reservations res ON res.room_id = r.id
            GROUP BY r.id, r.room_number, r.hotel_id
            ORDER BY reservation_count DESC
            LIMIT 5
                    """, nativeQuery = true)
                        List<Object[]> findMostReservedRooms();
}

