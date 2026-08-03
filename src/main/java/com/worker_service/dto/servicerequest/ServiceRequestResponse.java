package com.worker_service.dto.servicerequest;

import com.worker_service.enums.ServiceRequestStatus;

import java.time.LocalDateTime;

public record ServiceRequestResponse(

        Long id,

        Long workerId,

        String requestedName,

        String description,

        Long suggestedCategoryId,

        ServiceRequestStatus status,

        Long createdServiceId,

        Long reviewedBy,

        String reviewComment,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}