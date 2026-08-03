package com.worker_service.controller;

import com.worker_service.dto.common.PageResponse;
import com.worker_service.dto.servicecategory.ServiceCategoryCreateRequest;
import com.worker_service.dto.servicecategory.ServiceCategoryResponse;
import com.worker_service.dto.servicecategory.ServiceCategoryUpdateRequest;
import com.worker_service.service.ServiceCategoryService.ServiceCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/service-categories")
@RequiredArgsConstructor
public class ServiceCategoryController {

    private final ServiceCategoryService service;

    @PostMapping
    public ResponseEntity<ServiceCategoryResponse> create(
            @Valid @RequestBody ServiceCategoryCreateRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createCategory(request));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ServiceCategoryResponse> getById(
            @PathVariable Long categoryId) {

        return ResponseEntity.ok(
                service.getCategoryById(categoryId)
        );
    }

    @GetMapping
    public ResponseEntity<PageResponse<ServiceCategoryResponse>>
    getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        return ResponseEntity.ok(
                service.getCategories(pageable)
        );
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ServiceCategoryResponse> update(
            @PathVariable Long categoryId,
            @Valid @RequestBody ServiceCategoryUpdateRequest request) {

        return ResponseEntity.ok(
                service.updateCategory(
                        categoryId,
                        request
                )
        );
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deactivate(
            @PathVariable Long categoryId) {

        service.deactivateCategory(categoryId);

        return ResponseEntity.noContent().build();
    }
}