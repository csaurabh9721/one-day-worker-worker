package com.worker_service.repository;

import com.worker_service.entity.WorkerServiceArea;
import com.worker_service.enums.ServiceAreaType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkerServiceAreaRepository
        extends JpaRepository<WorkerServiceArea, Long> {

    List<WorkerServiceArea> findByWorkerId(Long workerId);

    List<WorkerServiceArea> findByWorkerIdAndActiveTrue(
            Long workerId
    );

    Page<WorkerServiceArea> findByCityIgnoreCaseAndActiveTrue(
            String city,
            Pageable pageable
    );

    Page<WorkerServiceArea> findByPincodeAndActiveTrue(
            String pincode,
            Pageable pageable
    );

    Page<WorkerServiceArea> findByAreaTypeAndActiveTrue(
            ServiceAreaType areaType,
            Pageable pageable
    );

    @Query("""
            SELECT wsa
            FROM WorkerServiceArea wsa
            JOIN FETCH wsa.worker w
            WHERE LOWER(wsa.city) = LOWER(:city)
              AND wsa.active = true
              AND w.active = true
            """)
    Page<WorkerServiceArea> findActiveWorkersByCity(
            @Param("city") String city,
            Pageable pageable
    );
}