package com.worker_service.dto;

import com.worker_service.entity.SubCategory;
import com.worker_service.entity.Worker;
import lombok.Builder;
import lombok.Data;

@Data
public class SkillResponseDto {
    private Long id;
    private Long workerId;
    private Long subCategoryId;
    private String subCategoryName;
    private Integer experienceYears;
    private Double hourlyRate;
    private Double fullDayRate;
}
