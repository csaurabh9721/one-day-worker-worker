package com.worker_service.dto.service;

import com.worker_service.enums.ServiceStatus;

public record ServiceResponse(

        Long id,

        String name,

        String description,

        Long subCategoryId,

        String subCategoryName,

        String iconUrl,

        ServiceStatus status,

        Boolean bookable,

        Boolean workerSelectable
) {
}