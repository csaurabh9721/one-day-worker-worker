package com.worker_service.dto.skill;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SkillUpdateRequest(

        @NotBlank
        @Size(max = 150)
        String name,

        @Size(max = 500)
        String description,

        Boolean active
) {
}