package com.worker_service.repository;

import com.worker_service.entity.WorkerSkill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WorkerSkillRepository
        extends JpaRepository<WorkerSkill, Long> {

    Optional<WorkerSkill> findByWorkerIdAndSkillId(
            Long workerId,
            Long skillId
    );

    boolean existsByWorkerIdAndSkillId(
            Long workerId,
            Long skillId
    );

    Page<WorkerSkill> findByWorkerId(
            Long workerId,
            Pageable pageable
    );

    Page<WorkerSkill> findBySkillId(
            Long skillId,
            Pageable pageable
    );

    @Query("""
            SELECT ws
            FROM WorkerSkill ws
            JOIN FETCH ws.skill s
            WHERE ws.worker.id = :workerId
            AND s.active = true
            """)
    Page<WorkerSkill> findActiveSkillsByWorker(
            @Param("workerId") Long workerId,
            Pageable pageable
    );
}