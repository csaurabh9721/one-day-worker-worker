package com.worker_service.controller;
import com.worker_service.dto.common.IdResponse;
import com.worker_service.dto.subcategory.ServiceSubcategoryRequest;
import com.worker_service.dto.subcategory.ServiceSubcategoryResponse;
import com.worker_service.service.subcategoryService.SubcategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/service-subcategories")
public class ServiceSubcategoryController {

    private final SubcategoryService service;

    @PostMapping
    public IdResponse create(
            @Valid @RequestBody ServiceSubcategoryRequest request) {

        return service.create(request);
    }

    @PutMapping("/{id}")
    public void update(
            @PathVariable Long id,
            @Valid @RequestBody ServiceSubcategoryRequest request) {

        service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id) {

        service.delete(id);
    }

    @GetMapping("/{id}")
    public ServiceSubcategoryResponse getById(
            @PathVariable Long id) {

        return service.getById(id);
    }

    @GetMapping
    public Page<ServiceSubcategoryResponse> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return service.getAll(pageable);
    }
    @GetMapping("/category/{categoryId}")
    public Page<ServiceSubcategoryResponse> getByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return service.getByCategory(categoryId, pageable);
    }

}