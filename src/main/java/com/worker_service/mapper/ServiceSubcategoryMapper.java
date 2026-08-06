package com.worker_service.mapper;


import com.worker_service.dto.subcategory.ServiceSubcategoryRequest;
import com.worker_service.dto.subcategory.ServiceSubcategoryResponse;
import com.worker_service.entity.serviceEntities.ServiceSubcategory;

public class ServiceSubcategoryMapper {

    private ServiceSubcategoryMapper() {
    }

    public static ServiceSubcategory toEntity(ServiceSubcategoryRequest request) {

        if (request == null) {
            return null;
        }

        return ServiceSubcategory.builder()
                .name(request.getName())
                .description(request.getDescription())
                .iconUrl(request.getIconUrl())
                .build();
    }

    public static ServiceSubcategoryResponse toResponse(ServiceSubcategory entity) {

        if (entity == null) {
            return null;
        }

        return ServiceSubcategoryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .iconUrl(entity.getIconUrl())
                .status(entity.getStatus())
                .categoryId(entity.getCategory().getId())
                .categoryName(entity.getCategory().getName())
                .build();
    }
}