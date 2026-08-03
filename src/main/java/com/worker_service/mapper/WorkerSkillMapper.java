package com.worker_service.mapper;

import com.worker_service.dto.skill.WorkerSkillResponse;
import com.worker_service.entity.WorkerSkill;
import org.springframework.stereotype.Component;

@Component
public class WorkerSkillMapper {

    public WorkerSkillResponse toResponse(
            WorkerSkill workerSkill) {

        if (workerSkill == null) {
            return null;
        }

        Long workerId = null;
        Long skillId = null;
        String skillName = null;

        if (workerSkill.getWorker() != null) {
            workerId =
                    workerSkill.getWorker().getId();
        }

        if (workerSkill.getSkill() != null) {
            skillId =
                    workerSkill.getSkill().getId();

            skillName =
                    workerSkill.getSkill().getName();
        }

        return new WorkerSkillResponse(
                workerSkill.getId(),
                skillId,
                skillName,
                workerSkill.getExperienceYears()
        );
    }
}