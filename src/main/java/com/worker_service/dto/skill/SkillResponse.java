package com.worker_service.dto.skill;

public record SkillResponse(

        Long id,

        String name,

        String description,

        Boolean active
) {
}