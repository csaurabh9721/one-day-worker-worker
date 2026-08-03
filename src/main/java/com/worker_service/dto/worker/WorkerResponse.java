package com.worker_service.dto.worker;

import com.worker_service.enums.AvailabilityStatus;
import com.worker_service.enums.Gender;
import com.worker_service.enums.WorkerStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record WorkerResponse(

        Long id,

        Long identityId,

        String firstName,

        String lastName,

        String phone,

        String email,

        String profileImageUrl,

        Gender gender,

        LocalDate dateOfBirth,

        WorkerStatus status,

        AvailabilityStatus availabilityStatus,

        Integer experienceYears,

        BigDecimal averageRating,

        Integer totalCompletedJobs,

        Boolean profileVerified,

        Boolean active,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}