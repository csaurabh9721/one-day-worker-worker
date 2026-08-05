package com.worker_service.dto.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ServiceCreateRequest(

        @NotBlank
        @Size(max = 150)
        String name,

        @Size(max = 1000)
        String description,

        @NotNull
        Long subCategoryId,

        @Size(max = 500)
        String iconUrl,

        Boolean bookable,

        Boolean workerSelectable
) {
}