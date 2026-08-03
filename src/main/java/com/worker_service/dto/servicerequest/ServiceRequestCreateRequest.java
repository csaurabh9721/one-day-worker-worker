package com.worker_service.dto.servicerequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ServiceRequestCreateRequest(

        @NotBlank
        @Size(max = 150)
        String requestedName,

        @Size(max = 1000)
        String description,

        Long suggestedCategoryId
) {
}