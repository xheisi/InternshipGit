package com.lhind.dto;
import com.lhind.entity.RoomStatus;
import com.lhind.entity.RoomType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    private int id;

    private int hotelId;

    @Positive(message = "roomNumber must be positive")
    private int roomNumber;

    @NotNull(message = "roomType is required")
    private RoomType roomType;

    @Positive(message = "capacity must be positive")
    private int capacity;

    @Positive(message = "pricePerNight must be positive")
    private double pricePerNight;

    private RoomStatus status;

}
