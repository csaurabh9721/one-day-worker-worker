package com.worker_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity
@Data
@Table(
        name = "worker_skill",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"worker_id", "subcategory_id"})
        },
        indexes = {
                @Index(name = "idx_subcategory", columnList = "subcategory_id")
        }
)
public class WorkerSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "worker_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Worker worker;

    @ManyToOne
    @JoinColumn(name = "subcategory_id", nullable = false)
    private SubCategory subCategory;

    private Integer experienceYears;
    private Double hourlyRate;
    private Double fullDayRate;
}
