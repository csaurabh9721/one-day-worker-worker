package com.worker_service.dto.worker;

import com.worker_service.enums.AvailabilityStatus;

import java.math.BigDecimal;

public record WorkerSummaryResponse(

        Long id,

        String firstName,

        String lastName,

        String profileImageUrl,

        Integer experienceYears,

        BigDecimal averageRating,

        Integer totalCompletedJobs,

        AvailabilityStatus availabilityStatus
) {
}