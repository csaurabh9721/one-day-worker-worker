package com.worker_service.repository;

import com.worker_service.entity.Service;
import com.worker_service.enums.ServiceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ServiceRepository
        extends JpaRepository<Service, Long> {

    Optional<Service> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    Page<Service> findByStatus(
            ServiceStatus status,
            Pageable pageable
    );

    Page<Service> findByCategoryId(
            Long categoryId,
            Pageable pageable
    );

    Page<Service> findByCategoryIdAndStatus(
            Long categoryId,
            ServiceStatus status,
            Pageable pageable
    );

    @Query("""
            SELECT s
            FROM Service s
            WHERE s.status = :status
              AND s.bookable = true
            """)
    Page<Service> findBookableServices(
            @Param("status") ServiceStatus status,
            Pageable pageable
    );

    @Query("""
            SELECT s
            FROM Service s
            WHERE   LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    OR LOWER(s.description)
                       LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    Page<Service> searchServices(
            @Param("keyword") String keyword,
            Pageable pageable
    );
}