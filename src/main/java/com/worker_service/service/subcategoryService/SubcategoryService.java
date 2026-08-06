package com.worker_service.service.subcategoryService;

import com.worker_service.dto.common.IdResponse;
import com.worker_service.dto.subcategory.ServiceSubcategoryRequest;
import com.worker_service.dto.subcategory.ServiceSubcategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubcategoryService {

    IdResponse create(ServiceSubcategoryRequest request);

    void update(
            Long id,
            ServiceSubcategoryRequest request
    );

    void delete(Long id);

    ServiceSubcategoryResponse getById(Long id);

    Page<ServiceSubcategoryResponse> getAll(Pageable pageable);

    Page<ServiceSubcategoryResponse> getByCategory(
            Long categoryId,
            Pageable pageable
    );

}
/**/