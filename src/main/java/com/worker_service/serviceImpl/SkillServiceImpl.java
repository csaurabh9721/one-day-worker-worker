package com.worker_service.serviceImpl;

import com.worker_service.dto.SkillPayloadDto;
import com.worker_service.dto.SkillResponseDto;
import com.worker_service.entity.SubCategory;
import com.worker_service.entity.Worker;
import com.worker_service.entity.WorkerSkill;
import com.worker_service.globleException.WorkerNotFoundException;
import com.worker_service.repository.SkillRepository;
import com.worker_service.repository.SubCategoryRepository;
import com.worker_service.repository.WorkerRepository;
import com.worker_service.service.SkillService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;
    private final WorkerRepository workerRepository;
    private final SubCategoryRepository subCategoryRepository;

    public SkillServiceImpl(SkillRepository skillRepository, WorkerRepository workerRepository, SubCategoryRepository subCategoryRepository) {
        this.skillRepository = skillRepository;
        this.workerRepository = workerRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    @Override
    public List<SkillResponseDto> getSkillByWorkerId(Long workerId) {
        List<WorkerSkill> skills = skillRepository.findByWorkerId(workerId);
        return skills.stream().map(this::toResponseDto).toList();
    }

    @Override
    @Transactional
    public SkillResponseDto saveWorkerSkill(Long workerId, SkillPayloadDto dto) {
        final Worker worker = workerRepository.findById(workerId).orElseThrow(() -> new WorkerNotFoundException("worker", workerId));
        final SubCategory subCategory = subCategoryRepository.findById(dto.getSubCategoryId()).orElseThrow(() -> new WorkerNotFoundException("subcategory", dto.getSubCategoryId()));
        if (skillRepository.findByWorkerIdAndSubCategoryId(workerId, dto.getSubCategoryId()).isPresent()) {
            throw new IllegalArgumentException("Skill already exists for worker");
        }
        WorkerSkill skill = new WorkerSkill();
        skill.setWorker(worker);
        skill.setSubCategory(subCategory);
        skill.setExperienceYears(dto.getExperienceYears());
        skill.setHourlyRate(dto.getHourlyRate());
        skill.setFullDayRate(dto.getFullDayRate());
        WorkerSkill savedSkill = skillRepository.save(skill);
        return toResponseDto(savedSkill);
    }

    @Override
    @Transactional
    public SkillResponseDto updateWorkerSkill(Long skillId, SkillPayloadDto dto) {
        final WorkerSkill existingSkill = skillRepository.findById(skillId).orElseThrow(() -> new WorkerNotFoundException("Skill", skillId));
        skillRepository.findByWorkerIdAndSubCategoryId(existingSkill.getWorker().getId(), dto.getSubCategoryId())
                .filter(skill -> !skill.getId().equals(existingSkill.getId()))
                .ifPresent(skill -> {
                    throw new IllegalArgumentException("Skill already exists for worker");
                });
        final SubCategory subCategory = subCategoryRepository.findById(dto.getSubCategoryId()).orElseThrow(() -> new WorkerNotFoundException("subcategory", dto.getSubCategoryId()));
        existingSkill.setSubCategory(subCategory);
        existingSkill.setExperienceYears(dto.getExperienceYears());
        existingSkill.setHourlyRate(dto.getHourlyRate());
        existingSkill.setFullDayRate(dto.getFullDayRate());
        WorkerSkill savedSkill = skillRepository.save(existingSkill);
        return toResponseDto(savedSkill);
    }

    @Override
    public Boolean deleteWorkerSkillBySkillId(Long skillId) {
        skillRepository.findById(skillId).orElseThrow(() -> new WorkerNotFoundException("Skill", skillId));
        skillRepository.deleteById(skillId);
        return true;
    }

    private SkillResponseDto toResponseDto(WorkerSkill skill) {
        SkillResponseDto dto = new SkillResponseDto();
        dto.setId(skill.getId());
        dto.setWorkerId(skill.getWorker().getId());
        dto.setSubCategoryId(skill.getSubCategory().getId());
        dto.setSubCategoryName(skill.getSubCategory().getName());
        dto.setExperienceYears(skill.getExperienceYears());
        dto.setHourlyRate(skill.getHourlyRate());
        dto.setFullDayRate(skill.getFullDayRate());
        return dto;
    }
}
