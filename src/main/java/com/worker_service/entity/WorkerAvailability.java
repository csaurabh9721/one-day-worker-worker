package com.worker_service.entity;
import com.worker_service.enums.DayOfWeekType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(
        name = "worker_availability",
        indexes = {
                @Index(
                        name = "idx_worker_availability_worker",
                        columnList = "worker_id"
                ),
                @Index(
                        name = "idx_worker_availability_day",
                        columnList = "day_of_week"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkerAvailability extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "worker_id",
            nullable = false
    )
    private Worker worker;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "day_of_week",
            nullable = false,
            length = 20
    )
    private DayOfWeekType dayOfWeek;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private Boolean active = true;
}
