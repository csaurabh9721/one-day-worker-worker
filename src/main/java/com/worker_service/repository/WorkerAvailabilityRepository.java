package com.worker_service.repository;

import com.worker_service.entity.WorkerAvailability;
import com.worker_service.enums.DayOfWeekType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkerAvailabilityRepository
        extends JpaRepository<WorkerAvailability, Long> {

    List<WorkerAvailability> findByWorkerId(
            Long workerId
    );

    List<WorkerAvailability> findByWorkerIdAndActiveTrue(
            Long workerId
    );

    Optional<WorkerAvailability> findByWorkerIdAndDayOfWeek(
            Long workerId,
            DayOfWeekType dayOfWeek
    );

    boolean existsByWorkerIdAndDayOfWeek(
            Long workerId,
            DayOfWeekType dayOfWeek
    );
}