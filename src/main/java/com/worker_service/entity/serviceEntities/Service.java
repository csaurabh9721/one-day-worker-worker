package com.worker_service.entity.serviceEntities;
import com.worker_service.entity.BaseEntity;
import com.worker_service.enums.ServiceStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "services",
        indexes = {
                @Index(
                        name = "idx_service_name",
                        columnList = "name"
                ),
                @Index(
                        name = "idx_service_subcategory",
                        columnList = "subcategory_id"
                ),
                @Index(
                        name = "idx_service_status",
                        columnList = "status"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Service extends BaseEntity {

    @Column(
            nullable = false,
            unique = true,
            length = 150
    )
    private String name;

    @Column(length = 1000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "subcategory_id",
            nullable = false
    )
    private ServiceSubcategory subcategory;

    @Column(length = 500)
    private String iconUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ServiceStatus status;

    /**
     * Can customer directly book this service?
     */
    @Column(nullable = false)
    private Boolean bookable = true;

    /**
     * Can worker select this service?
     */
    @Column(nullable = false)
    private Boolean workerSelectable = true;
}
