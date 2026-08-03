package com.worker_service.controller;

import com.worker_service.dto.document.WorkerDocumentCreateRequest;
import com.worker_service.dto.document.WorkerDocumentResponse;
import com.worker_service.dto.document.WorkerDocumentUpdateRequest;
import com.worker_service.enums.DocumentStatus;
import com.worker_service.service.WorkerDocumentService.WorkerDocumentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/workers/{workerId}/documents")
@RequiredArgsConstructor
public class WorkerDocumentController {

    private final WorkerDocumentService service;

    @PostMapping
    public ResponseEntity<WorkerDocumentResponse> upload(
            @PathVariable Long workerId,
            @Valid @RequestBody WorkerDocumentCreateRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        service.uploadDocument(
                                workerId,
                                request
                        )
                );
    }

    @GetMapping
    public ResponseEntity<List<WorkerDocumentResponse>>
    getDocuments(
            @PathVariable Long workerId) {

        return ResponseEntity.ok(
                service.getWorkerDocuments(workerId)
        );
    }

    @PutMapping("/{documentId}")
    public ResponseEntity<WorkerDocumentResponse> update(
            @PathVariable Long workerId,
            @PathVariable Long documentId,
            @Valid @RequestBody WorkerDocumentUpdateRequest request) {

        return ResponseEntity.ok(
                service.updateDocument(
                        workerId,
                        documentId,
                        request
                )
        );
    }

    @DeleteMapping("/{documentId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long workerId,
            @PathVariable Long documentId) {

        service.deleteDocument(
                workerId,
                documentId
        );

        return ResponseEntity.noContent().build();
    }

    /*
     * ADMIN endpoint
     */
    @PatchMapping("/admin/{documentId}/status")
    public ResponseEntity<WorkerDocumentResponse>
    updateStatus(
            @PathVariable Long documentId,
            @RequestParam DocumentStatus status,
            @RequestParam(required = false) String reviewComment,
            @RequestParam Long reviewerId) {

        return ResponseEntity.ok(
                service.updateDocumentStatus(
                        documentId,
                        status,
                        reviewComment,
                        reviewerId
                )
        );
    }
}