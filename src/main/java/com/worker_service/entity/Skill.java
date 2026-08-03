package com.worker_service.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "skills",
        indexes = {
                @Index(
                        name = "idx_skill_name",
                        columnList = "name"
                ),
                @Index(
                        name = "idx_skill_active",
                        columnList = "active"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skill extends BaseEntity {

    @Column(
            nullable = false,
            unique = true,
            length = 150
    )
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Boolean active = true;
}
