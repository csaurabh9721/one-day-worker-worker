package com.worker_service.dto.document;

import com.worker_service.enums.DocumentStatus;
import com.worker_service.enums.DocumentType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record WorkerDocumentResponse(

        Long id,

        DocumentType documentType,

        String maskedDocumentNumber,

        String documentUrl,

        DocumentStatus status,

        LocalDate issueDate,

        LocalDate expiryDate,

        LocalDateTime verifiedAt
) {
}