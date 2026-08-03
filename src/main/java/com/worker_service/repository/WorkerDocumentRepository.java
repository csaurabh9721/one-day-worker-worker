package com.worker_service.repository;

import com.worker_service.entity.WorkerDocument;
import com.worker_service.enums.DocumentStatus;
import com.worker_service.enums.DocumentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkerDocumentRepository
        extends JpaRepository<WorkerDocument, Long> {

    List<WorkerDocument> findByWorkerId(
            Long workerId
    );

    List<WorkerDocument> findByWorkerIdAndStatus(
            Long workerId,
            DocumentStatus status
    );

    Optional<WorkerDocument> findByWorkerIdAndDocumentType(
            Long workerId,
            DocumentType documentType
    );

    boolean existsByWorkerIdAndDocumentType(
            Long workerId,
            DocumentType documentType
    );

    Page<WorkerDocument> findByStatus(
            DocumentStatus status,
            Pageable pageable
    );
}