package com.worker_service.dto;

import lombok.Data;

@Data
public class SkillPayloadDto {
    private Long subCategoryId;     // which skill
    private Integer experienceYears;
    private Double hourlyRate;
    private Double fullDayRate;
}
