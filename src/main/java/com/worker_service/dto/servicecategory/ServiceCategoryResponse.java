package com.worker_service.dto.servicecategory;

public record ServiceCategoryResponse(

        Long id,

        String name,

        String description,

        String iconUrl,

        Boolean active
) {
}