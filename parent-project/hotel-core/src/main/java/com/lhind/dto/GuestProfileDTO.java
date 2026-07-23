package com.lhind.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestProfileDTO {

    private int id;
    private String address;
    private LocalDate dateOfBirth;
    private String nationality;
    private String preferredLanguage;
}
