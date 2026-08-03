package com.worker_service.dto.servicerequest;

import com.worker_service.enums.ServiceRequestStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ServiceRequestReviewRequest(

        @NotNull
        ServiceRequestStatus status,

        @Size(max = 500)
        String reviewComment
) {
}