package com.worker_service.controller;

import com.worker_service.dto.address.WorkerAddressCreateRequest;
import com.worker_service.dto.address.WorkerAddressResponse;
import com.worker_service.dto.address.WorkerAddressUpdateRequest;
import com.worker_service.service.WorkerAddressService.WorkerAddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/workers/{workerId}/addresses")
@RequiredArgsConstructor
public class WorkerAddressController {

    private final WorkerAddressService service;

    @PostMapping
    public ResponseEntity<WorkerAddressResponse> addAddress(
            @PathVariable Long workerId,
            @Valid @RequestBody WorkerAddressCreateRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        service.addAddress(
                                workerId,
                                request
                        )
                );
    }

    @GetMapping
    public ResponseEntity<List<WorkerAddressResponse>>
    getAddresses(
            @PathVariable Long workerId) {

        return ResponseEntity.ok(
                service.getWorkerAddresses(workerId)
        );
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<WorkerAddressResponse> getAddress(
            @PathVariable Long workerId,
            @PathVariable Long addressId) {

        return ResponseEntity.ok(
                service.getAddressById(
                        workerId,
                        addressId
                )
        );
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<WorkerAddressResponse> update(
            @PathVariable Long workerId,
            @PathVariable Long addressId,
            @Valid @RequestBody WorkerAddressUpdateRequest request) {

        return ResponseEntity.ok(
                service.updateAddress(
                        workerId,
                        addressId,
                        request
                )
        );
    }

    @PatchMapping("/{addressId}/primary")
    public ResponseEntity<Void> setPrimary(
            @PathVariable Long workerId,
            @PathVariable Long addressId) {

        service.setPrimaryAddress(
                workerId,
                addressId
        );

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> remove(
            @PathVariable Long workerId,
            @PathVariable Long addressId) {

        service.removeAddress(
                workerId,
                addressId
        );

        return ResponseEntity.noContent().build();
    }
}