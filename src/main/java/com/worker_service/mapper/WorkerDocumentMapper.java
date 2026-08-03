package com.worker_service.mapper;

import com.worker_service.dto.document.WorkerDocumentCreateRequest;
import com.worker_service.dto.document.WorkerDocumentResponse;
import com.worker_service.dto.document.WorkerDocumentUpdateRequest;
import com.worker_service.entity.WorkerDocument;
import org.springframework.stereotype.Component;

@Component
public class WorkerDocumentMapper {

    public WorkerDocument toEntity(
            WorkerDocumentCreateRequest request) {

        if (request == null) {
            return null;
        }

        WorkerDocument document =
                new WorkerDocument();

        document.setDocumentType(
                request.documentType()
        );

        document.setDocumentNumber(
                request.documentNumber()
        );

        document.setDocumentUrl(
                request.documentUrl()
        );

        return document;
    }

    public WorkerDocumentResponse toResponse(
            WorkerDocument document) {

        if (document == null) {
            return null;
        }

        Long workerId = null;

        if (document.getWorker() != null) {
            workerId =
                    document.getWorker().getId();
        }

        return new WorkerDocumentResponse(
                document.getId(),
                document.getDocumentType(),
                document.getDocumentNumber(),
                document.getDocumentUrl(),
                document.getStatus(),
                document.getIssueDate(),
                document.getExpiryDate(),
                document.getVerifiedAt()
        );
    }

    public void updateEntity(
            WorkerDocumentUpdateRequest request,
            WorkerDocument document) {

        if (request == null || document == null) {
            return;
        }

        if (request.documentNumber() != null) {
            document.setDocumentNumber(
                    request.documentNumber()
            );
        }

        if (request.documentUrl() != null) {
            document.setDocumentUrl(
                    request.documentUrl()
            );
        }
    }
}