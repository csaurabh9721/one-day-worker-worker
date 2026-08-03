package com.worker_service.entity;
import com.worker_service.enums.PricingType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "worker_services",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_worker_service",
                        columnNames = {
                                "worker_id",
                                "service_id"
                        }
                )
        },
        indexes = {
                @Index(
                        name = "idx_worker_service_worker",
                        columnList = "worker_id"
                ),
                @Index(
                        name = "idx_worker_service_service",
                        columnList = "service_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkerService extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "worker_id",
            nullable = false
    )
    private Worker worker;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "service_id",
            nullable = false
    )
    private Service service;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PricingType pricingType;

    /**
     * Advertised/base price.
     */
    @Column(
            precision = 12,
            scale = 2
    )
    private BigDecimal basePrice;

    /**
     * Hourly rate.
     */
    @Column(
            precision = 12,
            scale = 2
    )
    private BigDecimal hourlyRate;

    /**
     * Daily rate.
     */
    @Column(
            precision = 12,
            scale = 2
    )
    private BigDecimal dailyRate;

    /**
     * Price per unit.
     */
    @Column(
            precision = 12,
            scale = 2
    )
    private BigDecimal unitRate;

    /**
     * Example:
     * SQ_FT
     * ITEM
     * ROOM
     * VEHICLE
     */
    @Column(length = 50)
    private String unit;

    /**
     * Minimum price worker normally accepts.
     *
     * This should NOT be exposed publicly.
     */
    @Column(
            precision = 12,
            scale = 2
    )
    private BigDecimal minimumPrice;

    /**
     * Can customer negotiate?
     */
    @Column(nullable = false)
    private Boolean negotiable = false;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(length = 1000)
    private String description;
}