package com.worker_service.entity.serviceEntities;
import com.worker_service.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "service_categories",
        indexes = {
                @Index(
                        name = "idx_service_category_name",
                        columnList = "name"
                ),
                @Index(
                        name = "idx_service_category_active",
                        columnList = "active"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceCategory extends BaseEntity {

    @Column(
            nullable = false,
            unique = true,
            length = 100
    )
    private String name;

    @Column(length = 500)
    private String description;

    @Column(length = 500)
    private String iconUrl;

    @Column(nullable = false)
    private Boolean active = true;
}
