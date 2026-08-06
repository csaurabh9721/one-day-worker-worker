package com.worker_service.service.subcategoryService;


import com.worker_service.dto.common.IdResponse;
import com.worker_service.dto.subcategory.ServiceSubcategoryRequest;
import com.worker_service.dto.subcategory.ServiceSubcategoryResponse;
import com.worker_service.entity.serviceEntities.ServiceCategory;
import com.worker_service.entity.serviceEntities.ServiceSubcategory;
import com.worker_service.enums.ServiceStatus;
import com.worker_service.mapper.ServiceSubcategoryMapper;
import com.worker_service.repository.ServiceCategoryRepository;
import com.worker_service.repository.SubcategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceSubcategoryServiceImpl implements SubcategoryService {

    private final SubcategoryRepository repository;
    private final ServiceCategoryRepository categoryRepository;

    @Override
    public IdResponse create(ServiceSubcategoryRequest request) {

        if (repository.existsByNameIgnoreCaseAndCategory_Id(
                request.getName(),
                request.getCategoryId())) {

            throw new IllegalArgumentException(
                    "Subcategory already exists."
            );
        }

        ServiceCategory category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Category not found"));

        ServiceSubcategory entity =
                ServiceSubcategoryMapper.toEntity(request);

        entity.setCategory(category);
        entity.setStatus(ServiceStatus.ACTIVE);
        entity = repository.save(entity);

        return new IdResponse(entity.getId());
    }

    @Override
    public void update(
            Long id,
            ServiceSubcategoryRequest request) {

        ServiceSubcategory entity = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Subcategory not found"));

        ServiceCategory category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Category not found"));

        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setIconUrl(request.getIconUrl());
        entity.setCategory(category);
        entity.setStatus(ServiceStatus.ACTIVE);

        repository.save(entity);
    }

    @Override
    public void delete(Long id) {

        ServiceSubcategory entity = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Subcategory not found"));

        entity.setStatus(ServiceStatus.DELETED);

        repository.save(entity);
    }

    @Override
    public ServiceSubcategoryResponse getById(Long id) {

        ServiceSubcategory entity = repository
                .findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Subcategory not found"));

        return ServiceSubcategoryMapper.toResponse(entity);
    }

    @Override
    public Page<ServiceSubcategoryResponse> getAll(Pageable pageable) {

        return repository.findByActiveTrue(pageable)
                .map(ServiceSubcategoryMapper::toResponse);
    }

    @Override
    public Page<ServiceSubcategoryResponse> getByCategory(
            Long categoryId,
            Pageable pageable) {

        return repository.findByCategory_IdAndActiveTrue(
                        categoryId,
                        pageable)
                .map(ServiceSubcategoryMapper::toResponse);
    }
}