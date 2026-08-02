package de.lhind.internship.mini.project.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank @Email String email,
        @NotBlank String password,
        // Optional: "ROLE_ADMIN" or "ROLE_STAFF". Defaults to ROLE_STAFF if blank.
        String role
) {
}
