package com.lhind.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class MostReservedRoomDTO {
    private int roomId;
    private String roomNumber;
    private int hotelId;
    private long reservationCount;
}
