package com.worker_service.entity;
import com.worker_service.enums.AddressType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "worker_addresses",
        indexes = {
                @Index(
                        name = "idx_worker_address_worker",
                        columnList = "worker_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkerAddress extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "worker_id",
            nullable = false
    )
    private Worker worker;

    @Column(nullable = false, length = 255)
    private String addressLine1;

    @Column(length = 255)
    private String addressLine2;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(nullable = false, length = 100)
    private String state;

    @Column(nullable = false, length = 20)
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AddressType addressType;

    @Column(nullable = false)
    private Boolean primaryAddress = false;
}