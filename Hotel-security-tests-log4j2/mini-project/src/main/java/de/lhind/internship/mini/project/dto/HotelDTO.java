package de.lhind.internship.mini.project.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

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
