package de.lhind.internship.mini.project.dto;

import de.lhind.internship.mini.project.entity.ReservationStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    //non-null reservation dates, and
    // a positive number of guests.
    private int id;

    @NotNull(message = "Guest Id is required")
    private Integer guestId;

    @NotNull(message = "Room Id is required")
    private Integer roomId;

    @NotNull(message = "Check in date is required")
    private LocalDate checkInDate;

    @NotNull(message = "Check out date is required")
    private LocalDate checkOutDate;

    @Positive(message = "Number of guests must be positive")
    private int numberOfGuests;

    private double totalPrice;
    private ReservationStatus status;
    private LocalDateTime createdAt;
}
