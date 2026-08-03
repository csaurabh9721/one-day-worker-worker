package com.worker_service.service.SkillService;

import com.worker_service.dto.common.PageResponse;
import com.worker_service.dto.skill.SkillCreateRequest;
import com.worker_service.dto.skill.SkillResponse;
import com.worker_service.dto.skill.SkillUpdateRequest;
import org.springframework.data.domain.Pageable;

public interface SkillService {

    SkillResponse createSkill(
            SkillCreateRequest request
    );

    SkillResponse getSkillById(
            Long skillId
    );

    PageResponse<SkillResponse> getSkills(
            Pageable pageable
    );

    SkillResponse updateSkill(
            Long skillId,
            SkillUpdateRequest request
    );

    void deactivateSkill(
            Long skillId
    );
}