package com.worker_service.dto;

import com.worker_service.entity.SubCategory;
import com.worker_service.entity.Worker;
import lombok.Data;

@Data
public class WorkerSkillDto {
    private Long id;
    private Worker worker;
    private SubCategory subCategory;
    private Integer experienceYears;
    private Double hourlyRate;
    private Double fullDayRate;
}
