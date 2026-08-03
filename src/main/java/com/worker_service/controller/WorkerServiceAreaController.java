package com.worker_service.controller;

import com.worker_service.dto.servicearea.WorkerServiceAreaCreateRequest;
import com.worker_service.dto.servicearea.WorkerServiceAreaResponse;
import com.worker_service.dto.servicearea.WorkerServiceAreaUpdateRequest;
import com.worker_service.service.WorkerServiceAreaService.WorkerServiceAreaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/workers/{workerId}/service-areas")
@RequiredArgsConstructor
public class WorkerServiceAreaController {

    private final WorkerServiceAreaService service;

    @PostMapping
    public ResponseEntity<WorkerServiceAreaResponse> add(
            @PathVariable Long workerId,
            @Valid @RequestBody WorkerServiceAreaCreateRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        service.addServiceArea(
                                workerId,
                                request
                        )
                );
    }

    @GetMapping
    public ResponseEntity<List<WorkerServiceAreaResponse>>
    getAll(
            @PathVariable Long workerId) {

        return ResponseEntity.ok(
                service.getWorkerServiceAreas(workerId)
        );
    }

    @PutMapping("/{serviceAreaId}")
    public ResponseEntity<WorkerServiceAreaResponse> update(
            @PathVariable Long workerId,
            @PathVariable Long serviceAreaId,
            @Valid @RequestBody WorkerServiceAreaUpdateRequest request) {

        return ResponseEntity.ok(
                service.updateServiceArea(
                        workerId,
                        serviceAreaId,
                        request
                )
        );
    }

    @DeleteMapping("/{serviceAreaId}")
    public ResponseEntity<Void> remove(
            @PathVariable Long workerId,
            @PathVariable Long serviceAreaId) {

        service.removeServiceArea(
                workerId,
                serviceAreaId
        );

        return ResponseEntity.noContent().build();
    }
}