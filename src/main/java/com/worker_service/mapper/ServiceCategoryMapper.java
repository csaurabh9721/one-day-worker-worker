package com.worker_service.mapper;

import com.worker_service.dto.servicecategory.ServiceCategoryCreateRequest;
import com.worker_service.dto.servicecategory.ServiceCategoryResponse;
import com.worker_service.dto.servicecategory.ServiceCategoryUpdateRequest;
import com.worker_service.entity.ServiceCategory;
import org.springframework.stereotype.Component;

@Component
public class ServiceCategoryMapper {

    public ServiceCategory toEntity(
            ServiceCategoryCreateRequest request) {

        if (request == null) {
            return null;
        }

        ServiceCategory category =
                new ServiceCategory();

        category.setName(request.name());
        category.setDescription(request.description());

        return category;
    }

    public ServiceCategoryResponse toResponse(
            ServiceCategory category) {

        if (category == null) {
            return null;
        }

        return new ServiceCategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getIconUrl(),
                category.getActive()
        );
    }

    public void updateEntity(
            ServiceCategoryUpdateRequest request,
            ServiceCategory category) {

        if (request == null || category == null) {
            return;
        }

        if (request.name() != null) {
            category.setName(request.name());
        }

        if (request.description() != null) {
            category.setDescription(
                    request.description()
            );
        }
    }
}