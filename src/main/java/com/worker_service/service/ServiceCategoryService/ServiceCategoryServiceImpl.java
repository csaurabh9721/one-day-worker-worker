package com.worker_service.service.ServiceCategoryService;

import com.worker_service.dto.common.PageResponse;
import com.worker_service.dto.servicecategory.ServiceCategoryCreateRequest;
import com.worker_service.dto.servicecategory.ServiceCategoryResponse;
import com.worker_service.dto.servicecategory.ServiceCategoryUpdateRequest;
import com.worker_service.entity.ServiceCategory;
import com.worker_service.globleException.DuplicateResourceException;
import com.worker_service.globleException.ResourceNotFoundException;
import com.worker_service.mapper.ServiceCategoryMapper;
import com.worker_service.repository.ServiceCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServiceCategoryServiceImpl implements ServiceCategoryService {

    private final ServiceCategoryRepository repository;
    private final ServiceCategoryMapper mapper;

    @Override
    @Transactional
    public ServiceCategoryResponse createCategory(
            ServiceCategoryCreateRequest request) {

        if (repository.existsByNameIgnoreCase(request.name())) {
            throw new DuplicateResourceException(
                    "Service category already exists: " + request.name()
            );
        }

        ServiceCategory entity = mapper.toEntity(request);

        entity.setActive(true);

        ServiceCategory saved = repository.save(entity);

        return mapper.toResponse(saved);
    }

    @Override
    public ServiceCategoryResponse getCategoryById(Long categoryId) {

        ServiceCategory category = repository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Service category not found: " + categoryId
                        ));

        return mapper.toResponse(category);
    }

    @Override
    public PageResponse<ServiceCategoryResponse> getCategories(
            Pageable pageable) {

        Page<ServiceCategory> page =
                repository.findByActiveTrue(pageable);

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
    public ServiceCategoryResponse updateCategory(
            Long categoryId,
            ServiceCategoryUpdateRequest request) {

        ServiceCategory category = repository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Service category not found: " + categoryId
                        ));

        if (!category.getName().equalsIgnoreCase(request.name())
                && repository.existsByNameIgnoreCase(request.name())) {

            throw new DuplicateResourceException(
                    "Service category already exists: " + request.name()
            );
        }

        mapper.updateEntity(request, category);

        return mapper.toResponse(repository.save(category));
    }

    @Override
    @Transactional
    public void deactivateCategory(Long categoryId) {

        ServiceCategory category = repository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Service category not found: " + categoryId
                        ));

        category.setActive(false);
    }
}