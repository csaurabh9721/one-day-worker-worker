package com.worker_service.repository;

import com.worker_service.entity.serviceEntities.WorkerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WorkerServiceRepository
        extends JpaRepository<WorkerService, Long> {

    Optional<WorkerService> findByWorkerIdAndServiceId(
            Long workerId,
            Long serviceId
    );

    boolean existsByWorkerIdAndServiceId(
            Long workerId,
            Long serviceId
    );

    Page<WorkerService> findByWorkerId(
            Long workerId,
            Pageable pageable
    );

    Page<WorkerService> findByServiceId(
            Long serviceId,
            Pageable pageable
    );

    Page<WorkerService> findByWorkerIdAndActiveTrue(
            Long workerId,
            Pageable pageable
    );

    Page<WorkerService> findByServiceIdAndActiveTrue(
            Long serviceId,
            Pageable pageable
    );

    @Query("""
            SELECT ws
            FROM WorkerService ws
            JOIN FETCH ws.worker w
            JOIN FETCH ws.service s
            WHERE ws.service.id = :serviceId
              AND ws.active = true
              AND w.active = true
            """)
    Page<WorkerService> findActiveWorkersByService(
            @Param("serviceId") Long serviceId,
            Pageable pageable
    );

    @Query("""
            SELECT ws
            FROM WorkerService ws
            JOIN FETCH ws.worker w
            WHERE ws.service.id = :serviceId
              AND ws.negotiable = true
              AND ws.active = true
              AND w.active = true
            """)
    Page<WorkerService> findNegotiableWorkersByService(
            @Param("serviceId") Long serviceId,
            Pageable pageable
    );
}