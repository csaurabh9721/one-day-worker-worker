package com.worker_service.controller;
import com.worker_service.dto.common.PageResponse;
import com.worker_service.dto.servicerequest.ServiceRequestCreateRequest;
import com.worker_service.dto.servicerequest.ServiceRequestResponse;
import com.worker_service.dto.servicerequest.ServiceRequestReviewRequest;
import com.worker_service.service.ServiceRequestService.ServiceRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ServiceRequestController {

    private final ServiceRequestService service;

    @PostMapping("/workers/{workerId}/service-requests")
    public ResponseEntity<ServiceRequestResponse> create(
            @PathVariable Long workerId,
            @Valid @RequestBody ServiceRequestCreateRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        service.createRequest(
                                workerId,
                                request
                        )
                );
    }

    @GetMapping("/service-requests/{requestId}")
    public ResponseEntity<ServiceRequestResponse> getById(
            @PathVariable Long requestId) {

        return ResponseEntity.ok(
                service.getRequestById(requestId)
        );
    }

    @GetMapping("/workers/{workerId}/service-requests")
    public ResponseEntity<PageResponse<ServiceRequestResponse>>
    getWorkerRequests(
            @PathVariable Long workerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        return ResponseEntity.ok(
                service.getWorkerRequests(
                        workerId,
                        pageable
                )
        );
    }

    /*
     * ADMIN endpoint
     */
    @PatchMapping("/admin/service-requests/{requestId}")
    public ResponseEntity<ServiceRequestResponse>
    review(
            @PathVariable Long requestId,
            @Valid @RequestBody ServiceRequestReviewRequest request,
            @RequestParam Long reviewerId) {

        return ResponseEntity.ok(
                service.reviewRequest(
                        requestId,
                        reviewerId,
                        request
                )
        );
    }
}