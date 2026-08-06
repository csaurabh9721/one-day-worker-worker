package com.worker_service.repository;


import com.worker_service.entity.serviceEntities.Service;
import com.worker_service.entity.serviceEntities.ServiceSubcategory;
import com.worker_service.enums.ServiceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubcategoryRepository extends JpaRepository<ServiceSubcategory, Long> {


    Optional<ServiceSubcategory> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndCategory_Id(
            String name,
            Long categoryId
    );

    @Query("""
            SELECT sc
            FROM ServiceSubcategory sc
            WHERE sc.category.id = :categoryId
              AND sc.status = "ACTIVE"
            ORDER BY sc.name
            """)
    Page<ServiceSubcategory> findByCategory_IdAndActiveTrue(
            Long categoryId,
            Pageable pageable
    );
    @Query("""
            SELECT sc
            FROM ServiceSubcategory sc
            WHERE sc.id = :id
              AND sc.status = "ACTIVE"
            """)
    Optional<ServiceSubcategory> findByIdAndActiveTrue(Long id);



    @Query("""
            SELECT sc
            FROM ServiceSubcategory sc
            WHERE sc.status = "ACTIVE"
            ORDER BY sc.name
            """)
    Page<ServiceSubcategory> findByActiveTrue(
            Pageable pageable
    );

}
