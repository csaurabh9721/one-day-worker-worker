package com.worker_service.controller;

import com.worker_service.dto.common.PageResponse;
import com.worker_service.dto.worker.WorkerCreateRequest;
import com.worker_service.dto.worker.WorkerResponse;
import com.worker_service.dto.worker.WorkerSummaryResponse;
import com.worker_service.dto.worker.WorkerUpdateRequest;
import com.worker_service.service.WorkerService.WorkerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/workers")
@RequiredArgsConstructor
public class WorkerController {

    private final WorkerService workerService;

    @PostMapping
    public ResponseEntity<WorkerResponse> createWorker(
            @Valid @RequestBody WorkerCreateRequest request) {

        /*
         * In your real application, identityId should come
         * from the authenticated JWT, not from request body.
         *
         * Replace this with your SecurityContext/JWT extraction.
         */
        Long identityId = getCurrentIdentityId();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(workerService.createWorker(
                        request,
                        identityId
                ));
    }

    @GetMapping("/{workerId}")
    public ResponseEntity<WorkerResponse> getWorker(
            @PathVariable Long workerId) {

        return ResponseEntity.ok(
                workerService.getWorkerById(workerId)
        );
    }

    @GetMapping("/me")
    public ResponseEntity<WorkerResponse> getMyProfile() {

        Long identityId = getCurrentIdentityId();

        return ResponseEntity.ok(
                workerService.getMyWorkerProfile(identityId)
        );
    }

    @PutMapping("/{workerId}")
    public ResponseEntity<WorkerResponse> updateWorker(
            @PathVariable Long workerId,
            @Valid @RequestBody WorkerUpdateRequest request) {

        Long identityId = getCurrentIdentityId();

        return ResponseEntity.ok(
                workerService.updateWorker(
                        workerId,
                        identityId,
                        request
                )
        );
    }

    @DeleteMapping("/{workerId}")
    public ResponseEntity<Void> deactivateWorker(
            @PathVariable Long workerId) {

        Long identityId = getCurrentIdentityId();

        workerService.deactivateWorker(
                workerId,
                identityId
        );

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PageResponse<WorkerSummaryResponse>>
    searchWorkers(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        return ResponseEntity.ok(
                workerService.searchWorkers(
                        keyword,
                        pageable
                )
        );
    }

    private Long getCurrentIdentityId() {

        /*
         * TODO:
         * Extract identityId from authenticated JWT.
         *
         * Example:
         * return jwtService.getCurrentIdentityId();
         */

        throw new UnsupportedOperationException(
                "JWT identity extraction not implemented"
        );
    }
}