package com.worker_service.dto.worker;

import com.worker_service.enums.Gender;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record WorkerCreateRequest(
        @NotNull
        Long identityId,
        @NotBlank
        @Size(max = 100)
        String firstName,

        @NotBlank
        @Size(max = 100)
        String lastName,

        @Pattern(regexp = "^[0-9]{10}$")
        @Size(min = 10, max = 20, message = "Phone number must be between 10 and 20 digits")
        String phone

        ) {
}