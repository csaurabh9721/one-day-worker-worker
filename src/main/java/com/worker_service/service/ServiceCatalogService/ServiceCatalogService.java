package com.worker_service.service.ServiceCatalogService;

import com.worker_service.dto.common.PageResponse;
import com.worker_service.dto.service.ServiceCreateRequest;
import com.worker_service.dto.service.ServiceResponse;
import com.worker_service.dto.service.ServiceUpdateRequest;
import org.springframework.data.domain.Pageable;

public interface ServiceCatalogService {

    ServiceResponse createService(
            ServiceCreateRequest request
    );

    ServiceResponse getServiceById(
            Long serviceId
    );

    PageResponse<ServiceResponse> getServices(
            Long categoryId,
            Pageable pageable
    );

    PageResponse<ServiceResponse> searchServices(
            String keyword,
            Pageable pageable
    );

    ServiceResponse updateService(
            Long serviceId,
            ServiceUpdateRequest request
    );

    void deactivateService(
            Long serviceId
    );
}