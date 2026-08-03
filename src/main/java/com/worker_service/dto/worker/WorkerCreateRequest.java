package com.worker_service.dto.worker;

import com.worker_service.enums.Gender;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record WorkerCreateRequest(

        @NotBlank
        @Size(max = 100)
        String firstName,

        @NotBlank
        @Size(max = 100)
        String lastName,

        @Email
        @Size(max = 150)
        String email,

        @Pattern(regexp = "^[0-9]{10}$")
        String phone,

        Gender gender,

        @Past
        LocalDate dateOfBirth,

        @Min(0)
        @Max(60)
        Integer experienceYears,

        @Size(max = 500)
        String profileImageUrl
) {
}