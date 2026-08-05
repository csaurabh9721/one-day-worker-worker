package com.worker_service.repository;

import com.worker_service.entity.serviceEntities.ServiceRequest;
import com.worker_service.enums.ServiceRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRequestRepository
        extends JpaRepository<ServiceRequest, Long> {

    Page<ServiceRequest> findByWorkerId(
            Long workerId,
            Pageable pageable
    );

    Page<ServiceRequest> findByWorkerIdAndStatus(
            Long workerId,
            ServiceRequestStatus status,
            Pageable pageable
    );

    Page<ServiceRequest> findByStatus(
            ServiceRequestStatus status,
            Pageable pageable
    );

    boolean existsByWorkerIdAndRequestedNameIgnoreCaseAndStatus(
            Long workerId,
            String requestedName,
            ServiceRequestStatus status
    );
}