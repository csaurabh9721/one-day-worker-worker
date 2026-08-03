package com.worker_service.mapper;

import com.worker_service.dto.skill.SkillCreateRequest;
import com.worker_service.dto.skill.SkillResponse;
import com.worker_service.dto.skill.SkillUpdateRequest;
import com.worker_service.entity.Skill;
import org.springframework.stereotype.Component;

@Component
public class SkillMapper {

    public Skill toEntity(
            SkillCreateRequest request) {

        if (request == null) {
            return null;
        }

        Skill skill = new Skill();

        skill.setName(request.name());
        skill.setDescription(
                request.description()
        );

        return skill;
    }

    public SkillResponse toResponse(
            Skill skill) {

        if (skill == null) {
            return null;
        }

        return new SkillResponse(
                skill.getId(),
                skill.getName(),
                skill.getDescription(),
                skill.getActive()
        );
    }

    public void updateEntity(
            SkillUpdateRequest request,
            Skill skill) {

        if (request == null || skill == null) {
            return;
        }

        if (request.name() != null) {
            skill.setName(request.name());
        }

        if (request.description() != null) {
            skill.setDescription(
                    request.description()
            );
        }
    }
}