package com.worker_service.repository;

import com.worker_service.entity.WorkerSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<WorkerSkill,Long> {
    List<WorkerSkill> findByWorkerId(Long workerId);
    Optional<WorkerSkill> findByWorkerIdAndSubCategoryId(Long workerId, Long subCategoryId);

}
