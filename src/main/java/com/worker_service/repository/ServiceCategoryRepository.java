package com.worker_service.repository;

import com.worker_service.entity.serviceEntities.ServiceCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceCategoryRepository
        extends JpaRepository<ServiceCategory, Long> {

    boolean existsByNameIgnoreCase(String name);

    Page<ServiceCategory> findByActiveTrue(Pageable pageable);
}