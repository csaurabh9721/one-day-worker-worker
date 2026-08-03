package com.worker_service.controller;

import com.worker_service.dto.common.PageResponse;
import com.worker_service.dto.workerservice.WorkerServiceCreateRequest;
import com.worker_service.dto.workerservice.WorkerServiceResponse;
import com.worker_service.dto.workerservice.WorkerServiceUpdateRequest;
import com.worker_service.service.WorkerServiceManager.WorkerServiceManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/workers/{workerId}/services")
@RequiredArgsConstructor
public class WorkerServiceController {

    private final WorkerServiceManager service;

    @PostMapping
    public ResponseEntity<WorkerServiceResponse> addService(
            @PathVariable Long workerId,
            @Valid @RequestBody WorkerServiceCreateRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        service.addService(
                                workerId,
                                request
                        )
                );
    }

    @GetMapping
    public ResponseEntity<PageResponse<WorkerServiceResponse>>
    getServices(
            @PathVariable Long workerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        return ResponseEntity.ok(
                service.getWorkerServices(
                        workerId,
                        pageable
                )
        );
    }

    @GetMapping("/{workerServiceId}")
    public ResponseEntity<WorkerServiceResponse>
    getService(
            @PathVariable Long workerId,
            @PathVariable Long workerServiceId) {

        return ResponseEntity.ok(
                service.getWorkerService(
                        workerId,
                        workerServiceId
                )
        );
    }

    @PutMapping("/{workerServiceId}")
    public ResponseEntity<WorkerServiceResponse> update(
            @PathVariable Long workerId,
            @PathVariable Long workerServiceId,
            @Valid @RequestBody WorkerServiceUpdateRequest request) {

        return ResponseEntity.ok(
                service.updateService(
                        workerId,
                        workerServiceId,
                        request
                )
        );
    }

    @DeleteMapping("/{workerServiceId}")
    public ResponseEntity<Void> remove(
            @PathVariable Long workerId,
            @PathVariable Long workerServiceId) {

        service.removeService(
                workerId,
                workerServiceId
        );

        return ResponseEntity.noContent().build();
    }
}