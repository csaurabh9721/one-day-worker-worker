package com.worker_service.dto.document;

import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record WorkerDocumentUpdateRequest(

        @Size(max = 255)
        String documentNumber,

        @Size(max = 1000)
        String documentUrl,

        LocalDate issueDate,

        LocalDate expiryDate
) {
}