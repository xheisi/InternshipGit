package com.lhind.dto;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelDTO {
    private int id;

    @NotBlank(message = "name is required")
    private String name;

    private String city;

    private String address;

    @Min(value = 1, message = "starRating must be between 1 and 5")
    @Max(value = 5, message = "starRating must be between 1 and 5")
    private int starRating;
}
