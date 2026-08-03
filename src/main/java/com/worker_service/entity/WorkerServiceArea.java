package com.worker_service.entity;
import com.worker_service.enums.ServiceAreaType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "worker_service_areas",
        indexes = {
                @Index(
                        name = "idx_service_area_worker",
                        columnList = "worker_id"
                ),
                @Index(
                        name = "idx_service_area_city",
                        columnList = "city"
                ),
                @Index(
                        name = "idx_service_area_pincode",
                        columnList = "pincode"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkerServiceArea extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "worker_id",
            nullable = false
    )
    private Worker worker;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ServiceAreaType areaType;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String state;

    @Column(length = 20)
    private String pincode;

    @Column(
            precision = 10,
            scale = 7
    )
    private BigDecimal latitude;

    @Column(
            precision = 10,
            scale = 7
    )
    private BigDecimal longitude;

    /**
     * Radius in kilometers.
     */
    @Column(
            precision = 8,
            scale = 2
    )
    private BigDecimal radiusKm;

    @Column(nullable = false)
    private Boolean active = true;
}
