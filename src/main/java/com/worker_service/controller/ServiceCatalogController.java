package com.worker_service.controller;

import com.worker_service.dto.common.PageResponse;
import com.worker_service.dto.service.ServiceCreateRequest;
import com.worker_service.dto.service.ServiceResponse;
import com.worker_service.dto.service.ServiceUpdateRequest;
import com.worker_service.service.ServiceCatalogService.ServiceCatalogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class ServiceCatalogController  {

    private final ServiceCatalogService service;

    @PostMapping
    public ResponseEntity<ServiceResponse> create(
            @Valid @RequestBody ServiceCreateRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createService(request));
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<ServiceResponse> getById(
            @PathVariable Long serviceId) {

        return ResponseEntity.ok(
                service.getServiceById(serviceId)
        );
    }

    @GetMapping
    public ResponseEntity<PageResponse<ServiceResponse>>
    getServices(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        return ResponseEntity.ok(
                service.getServices(
                        categoryId,
                        pageable
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<ServiceResponse>>
    search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        return ResponseEntity.ok(
                service.searchServices(
                        keyword,
                        pageable
                )
        );
    }

    @PutMapping("/{serviceId}")
    public ResponseEntity<ServiceResponse> update(
            @PathVariable Long serviceId,
            @Valid @RequestBody ServiceUpdateRequest request) {

        return ResponseEntity.ok(
                service.updateService(
                        serviceId,
                        request
                )
        );
    }

    @DeleteMapping("/{serviceId}")
    public ResponseEntity<Void> deactivate(
            @PathVariable Long serviceId) {

        service.deactivateService(serviceId);

        return ResponseEntity.noContent().build();
    }
}