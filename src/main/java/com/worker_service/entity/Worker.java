package com.worker_service.entity;
import com.worker_service.enums.AvailabilityStatus;
import com.worker_service.enums.Gender;
import com.worker_service.enums.WorkerStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
        name = "workers",
        indexes = {
                @Index(
                        name = "idx_worker_identity_id",
                        columnList = "identity_id"
                ),
                @Index(
                        name = "idx_worker_status",
                        columnList = "status"
                ),
                @Index(
                        name = "idx_worker_availability",
                        columnList = "availability_status"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Worker extends BaseEntity {

    /**
     * Identity ID from Auth Service.
     */
    @Column(
            name = "identity_id",
            nullable = false,
            unique = true,
            updatable = false
    )
    private Long identityId;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(length = 20)
    private String phone;

    @Column(length = 150)
    private String email;

    @Column(length = 500)
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Gender gender;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private WorkerStatus status;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "availability_status",
            nullable = false,
            length = 30
    )
    private AvailabilityStatus availabilityStatus;

    private Integer experienceYears;

    /**
     * Rating summary.
     *
     * Actual ratings are owned by Rating Service.
     */
    @Column(
            precision = 3,
            scale = 2
    )
    private BigDecimal averageRating;

    @Column(nullable = false)
    private Integer totalCompletedJobs = 0;

    @Column(nullable = false)
    private Boolean profileVerified = false;

    @Column(nullable = false)
    private Boolean active = true;
}