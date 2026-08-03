package com.worker_service.repository;
import com.worker_service.entity.Worker;
import com.worker_service.enums.AvailabilityStatus;
import com.worker_service.enums.WorkerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

    Optional<Worker> findByIdentityId(Long identityId);

    boolean existsByIdentityId(Long identityId);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByPhone(String phone);

    Optional<Worker> findByEmailIgnoreCase(String email);

    Page<Worker> findByStatus(
            WorkerStatus status,
            Pageable pageable
    );

    Page<Worker> findByStatusAndAvailabilityStatus(
            WorkerStatus status,
            AvailabilityStatus availabilityStatus,
            Pageable pageable
    );

    @Query("""
            SELECT w
            FROM Worker w
            WHERE w.status = :status
              AND w.availabilityStatus = :availabilityStatus
              AND w.active = true
            """)
    Page<Worker> findActiveAvailableWorkers(
            @Param("status") WorkerStatus status,
            @Param("availabilityStatus") AvailabilityStatus availabilityStatus,
            Pageable pageable
    );

    @Query("""
            SELECT w
            FROM Worker w
            WHERE w.active = true
              AND (
                    LOWER(w.firstName) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    OR LOWER(w.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    OR LOWER(w.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
                  )
            """)
    Page<Worker> searchWorkers(
            @Param("keyword") String keyword,
            Pageable pageable
    );
}
