package com.worker_service.service.ServiceCatalogService;

import com.worker_service.dto.common.PageResponse;
import com.worker_service.dto.service.ServiceCreateRequest;
import com.worker_service.dto.service.ServiceResponse;
import com.worker_service.dto.service.ServiceUpdateRequest;
import com.worker_service.enums.ServiceStatus;
import com.worker_service.globleException.DuplicateResourceException;
import com.worker_service.globleException.ResourceNotFoundException;
import com.worker_service.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import com.worker_service.mapper.ServiceMapper ;
import com.worker_service.entity.Service ;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServiceCatalogServiceImpl
        implements ServiceCatalogService {

    private final ServiceRepository repository;
    private final ServiceMapper mapper;

    @Override
    @Transactional
    public ServiceResponse createService(
            ServiceCreateRequest request) {

        if (repository.existsByNameIgnoreCase(request.name())) {
            throw new DuplicateResourceException(
                    "Service already exists: " + request.name()
            );
        }

        Service service = mapper.toEntity(request);

        service.setStatus(ServiceStatus.ACTIVE);

        Service saved = repository.save(service);

        return mapper.toResponse(saved);
    }

    @Override
    public ServiceResponse getServiceById(Long serviceId) {

        Service service = repository.findById(serviceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Service not found: " + serviceId
                        ));

        return mapper.toResponse(service);
    }

    @Override
    public PageResponse<ServiceResponse> getServices(
            Long categoryId,
            Pageable pageable) {

        Page<Service> page;

        if (categoryId != null) {
            page = repository.findByCategoryId(
                    categoryId,
                    pageable
            );
        } else {
            page = repository.findAll(pageable);
        }

        return new PageResponse<>(
                page.getContent()
                        .stream()
                        .map(mapper::toResponse)
                        .toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }

    @Override
    public PageResponse<ServiceResponse> searchServices(
            String keyword,
            Pageable pageable) {

        Page<Service> page =
                repository.searchServices(keyword, pageable);

        return new PageResponse<>(
                page.getContent()
                        .stream()
                        .map(mapper::toResponse)
                        .toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }

    @Override
    @Transactional
    public ServiceResponse updateService(
            Long serviceId,
            ServiceUpdateRequest request) {

        Service service = repository.findById(serviceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Service not found: " + serviceId
                        ));

        if (!service.getName().equalsIgnoreCase(request.name())
                && repository.existsByNameIgnoreCase(request.name())) {

            throw new DuplicateResourceException(
                    "Service already exists: " + request.name()
            );
        }

        mapper.updateEntity(request, service);

        return mapper.toResponse(repository.save(service));
    }

    @Override
    @Transactional
    public void deactivateService(Long serviceId) {

        Service service = repository.findById(serviceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Service not found: " + serviceId
                        ));

        service.setStatus(ServiceStatus.INACTIVE);
    }
}
