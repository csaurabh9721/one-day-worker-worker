package com.worker_service.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "worker_skills",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_worker_skill",
                        columnNames = {
                                "worker_id",
                                "skill_id"
                        }
                )
        },
        indexes = {
                @Index(
                        name = "idx_worker_skill_worker",
                        columnList = "worker_id"
                ),
                @Index(
                        name = "idx_worker_skill_skill",
                        columnList = "skill_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkerSkill extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "worker_id",
            nullable = false
    )
    private Worker worker;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "skill_id",
            nullable = false
    )
    private Skill skill;

    private Integer experienceYears;
}