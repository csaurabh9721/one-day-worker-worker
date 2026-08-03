package com.worker_service.dto.skill;

public record WorkerSkillResponse(

        Long id,

        Long skillId,

        String skillName,

        Integer experienceYears
) {
}