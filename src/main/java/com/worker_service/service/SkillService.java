package com.worker_service.service;

import com.worker_service.dto.SkillPayloadDto;
import com.worker_service.dto.SkillResponseDto;

import java.util.List;

public interface SkillService {
    List<SkillResponseDto> getSkillByWorkerId(Long workerId);

    SkillResponseDto saveWorkerSkill(Long workerId, SkillPayloadDto dto);

    SkillResponseDto updateWorkerSkill(Long skillId, SkillPayloadDto dto);

    Boolean deleteWorkerSkillBySkillId(Long skillId);

}
