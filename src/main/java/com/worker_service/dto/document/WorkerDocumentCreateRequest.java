package com.worker_service.dto.document;

import com.worker_service.enums.DocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record WorkerDocumentCreateRequest(

        @NotNull
        DocumentType documentType,

        @Size(max = 255)
        String documentNumber,

        @NotBlank
        @Size(max = 1000)
        String documentUrl,

        LocalDate issueDate,

        LocalDate expiryDate
) {
}