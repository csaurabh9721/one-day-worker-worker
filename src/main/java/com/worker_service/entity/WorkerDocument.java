package com.worker_service.entity;
import com.worker_service.enums.DocumentStatus;
import com.worker_service.enums.DocumentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "worker_documents",
        indexes = {
                @Index(
                        name = "idx_worker_document_worker",
                        columnList = "worker_id"
                ),
                @Index(
                        name = "idx_worker_document_status",
                        columnList = "status"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkerDocument extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "worker_id",
            nullable = false
    )
    private Worker worker;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private DocumentType documentType;

    /**
     * Sensitive information.
     * In production, consider encryption/masking.
     */
    @Column(length = 255)
    private String documentNumber;

    @Column(length = 1000)
    private String documentUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private DocumentStatus status;

    private LocalDate issueDate;

    private LocalDate expiryDate;

    private LocalDateTime verifiedAt;

    /**
     * Admin/User ID that verified the document.
     */
    private Long verifiedBy;

    @Column(length = 500)
    private String rejectionReason;
}