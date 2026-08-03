package com.worker_service.service.WorkerDocumentService;

import com.worker_service.dto.document.WorkerDocumentCreateRequest;
import com.worker_service.dto.document.WorkerDocumentResponse;
import com.worker_service.dto.document.WorkerDocumentUpdateRequest;
import com.worker_service.enums.DocumentStatus;

import java.util.List;

public interface WorkerDocumentService {

    WorkerDocumentResponse uploadDocument(
            Long workerId,
            WorkerDocumentCreateRequest request
    );

    List<WorkerDocumentResponse> getWorkerDocuments(
            Long workerId
    );

    WorkerDocumentResponse updateDocument(
            Long workerId,
            Long documentId,
            WorkerDocumentUpdateRequest request
    );

    void deleteDocument(
            Long workerId,
            Long documentId
    );

    WorkerDocumentResponse updateDocumentStatus(
            Long documentId,
            DocumentStatus status,
            String reviewComment,
            Long reviewerId
    );
}