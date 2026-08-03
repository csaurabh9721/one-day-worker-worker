package com.worker_service.controller;

import com.worker_service.dto.availability.WorkerAvailabilityRequest;
import com.worker_service.dto.availability.WorkerAvailabilityResponse;
import com.worker_service.service.WorkerAvailabilityService.WorkerAvailabilityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/workers/{workerId}/availability")
@RequiredArgsConstructor
public class WorkerAvailabilityController {

    private final WorkerAvailabilityService service;

    @PostMapping
    public ResponseEntity<WorkerAvailabilityResponse> create(
            @PathVariable Long workerId,
            @Valid @RequestBody WorkerAvailabilityRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        service.setAvailability(
                                workerId,
                                request
                        )
                );
    }

    @GetMapping
    public ResponseEntity<List<WorkerAvailabilityResponse>>
    getAll(
            @PathVariable Long workerId) {

        return ResponseEntity.ok(
                service.getWorkerAvailability(workerId)
        );
    }

    @PutMapping("/{availabilityId}")
    public ResponseEntity<WorkerAvailabilityResponse> update(
            @PathVariable Long workerId,
            @PathVariable Long availabilityId,
            @Valid @RequestBody WorkerAvailabilityRequest request) {

        return ResponseEntity.ok(
                service.updateAvailability(
                        workerId,
                        availabilityId,
                        request
                )
        );
    }

    @DeleteMapping("/{availabilityId}")
    public ResponseEntity<Void> remove(
            @PathVariable Long workerId,
            @PathVariable Long availabilityId) {

        service.removeAvailability(
                workerId,
                availabilityId
        );

        return ResponseEntity.noContent().build();
    }
}