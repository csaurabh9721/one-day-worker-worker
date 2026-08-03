package com.worker_service.service.ServiceCategoryService;

import com.worker_service.dto.common.PageResponse;
import com.worker_service.dto.servicecategory.ServiceCategoryCreateRequest;
import com.worker_service.dto.servicecategory.ServiceCategoryResponse;
import com.worker_service.dto.servicecategory.ServiceCategoryUpdateRequest;
import org.springframework.data.domain.Pageable;

public interface ServiceCategoryService {

    ServiceCategoryResponse createCategory(
            ServiceCategoryCreateRequest request
    );

    ServiceCategoryResponse getCategoryById(
            Long categoryId
    );

    PageResponse<ServiceCategoryResponse> getCategories(
            Pageable pageable
    );

    ServiceCategoryResponse updateCategory(
            Long categoryId,
            ServiceCategoryUpdateRequest request
    );

    void deactivateCategory(
            Long categoryId
    );
}