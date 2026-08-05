package com.worker_service.entity.serviceEntities;

import com.worker_service.entity.BaseEntity;
import com.worker_service.entity.Worker;
import com.worker_service.enums.ServiceRequestStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "service_requests",
        indexes = {
                @Index(
                        name = "idx_service_request_worker",
                        columnList = "worker_id"
                ),
                @Index(
                        name = "idx_service_request_status",
                        columnList = "status"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceRequest extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "worker_id",
            nullable = false
    )
    private Worker worker;

    @Column(
            nullable = false,
            length = 150
    )
    private String requestedName;

    @Column(length = 1000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suggested_category_id")
    private ServiceCategory suggestedCategory;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ServiceRequestStatus status;

    /**
     * Populated after approval.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_service_id")
    private Service createdService;

    /**
     * Admin ID.
     */
    private Long reviewedBy;

    @Column(length = 500)
    private String reviewComment;
}