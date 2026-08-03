package com.worker_service.service.WorkerSkillService;

import com.worker_service.dto.common.PageResponse;
import com.worker_service.dto.skill.WorkerSkillResponse;
import org.springframework.data.domain.Pageable;

public interface WorkerSkillService {

    WorkerSkillResponse addSkill(
            Long workerId,
            Long skillId,
            Integer experienceYears
    );

    PageResponse<WorkerSkillResponse> getWorkerSkills(
            Long workerId,
            Pageable pageable
    );

    WorkerSkillResponse updateSkill(
            Long workerId,
            Long workerSkillId,
            Integer experienceYears
    );

    void removeSkill(
            Long workerId,
            Long workerSkillId
    );
}