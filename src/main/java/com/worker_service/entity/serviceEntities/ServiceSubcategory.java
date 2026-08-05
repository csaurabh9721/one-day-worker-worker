package com.worker_service.entity.serviceEntities;

import com.worker_service.entity.BaseEntity;
import com.worker_service.enums.ServiceStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "service_subcategories",
        indexes = {
                @Index(
                        name = "idx_subcategory_name",
                        columnList = "name"
                ),
                @Index(
                        name = "idx_subcategory_category",
                        columnList = "category_id"
                ),
                @Index(
                        name = "idx_subcategory_status",
                        columnList = "status"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceSubcategory extends BaseEntity {

    @Column(
            nullable = false,
            length = 150
    )
    private String name;

    @Column(length = 1000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "category_id",
            nullable = false
    )
    private ServiceCategory category;

    @Column(length = 500)
    private String iconUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ServiceStatus status;
}
