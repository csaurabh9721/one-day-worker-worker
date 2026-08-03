package com.worker_service.controller;

import com.worker_service.dto.common.PageResponse;
import com.worker_service.dto.workerservice.WorkerServiceResponse;
import com.worker_service.service.WorkerServiceManager.WorkerServiceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class ServiceWorkerSearchController {

    private final WorkerServiceManager service;

    @GetMapping("/{serviceId}/workers")
    public ResponseEntity<PageResponse<WorkerServiceResponse>>
    findWorkers(
            @PathVariable Long serviceId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        return ResponseEntity.ok(
                service.findWorkersByService(
                        serviceId,
                        pageable
                )
        );
    }
}