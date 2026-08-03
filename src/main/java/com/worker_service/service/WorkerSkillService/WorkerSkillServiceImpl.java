package com.worker_service.service.WorkerSkillService;
import com.worker_service.dto.common.PageResponse;
import com.worker_service.dto.skill.WorkerSkillResponse;
import com.worker_service.entity.Skill;
import com.worker_service.entity.Worker;
import com.worker_service.entity.WorkerSkill;
import com.worker_service.globleException.DuplicateResourceException;
import com.worker_service.globleException.ResourceNotFoundException;
import com.worker_service.mapper.WorkerSkillMapper;
import com.worker_service.repository.SkillRepository;
import com.worker_service.repository.WorkerRepository;
import com.worker_service.repository.WorkerSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkerSkillServiceImpl
        implements WorkerSkillService {

    private final WorkerSkillRepository repository;
    private final WorkerRepository workerRepository;
    private final SkillRepository skillRepository;
    private final WorkerSkillMapper mapper;

    @Override
    @Transactional
    public WorkerSkillResponse addSkill(
            Long workerId,
            Long skillId,
            Integer experienceYears) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Worker not found: " + workerId
                        ));

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Skill not found: " + skillId
                        ));

        if (repository.existsByWorkerIdAndSkillId(
                workerId,
                skillId)) {

            throw new DuplicateResourceException(
                    "Worker already has this skill"
            );
        }

        WorkerSkill workerSkill = new WorkerSkill();

        workerSkill.setWorker(worker);
        workerSkill.setSkill(skill);
        workerSkill.setExperienceYears(experienceYears);

        return mapper.toResponse(
                repository.save(workerSkill)
        );
    }

    @Override
    public PageResponse<WorkerSkillResponse> getWorkerSkills(
            Long workerId,
            Pageable pageable) {

        Page<WorkerSkill> page =
                repository.findActiveSkillsByWorker(
                        workerId,
                        pageable
                );

        return new PageResponse<>(
                page.getContent()
                        .stream()
                        .map(mapper::toResponse)
                        .toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }

    @Override
    @Transactional
    public WorkerSkillResponse updateSkill(
            Long workerId,
            Long workerSkillId,
            Integer experienceYears) {

        WorkerSkill workerSkill =
                findWorkerSkill(
                        workerId,
                        workerSkillId
                );

        workerSkill.setExperienceYears(
                experienceYears
        );

        return mapper.toResponse(
                repository.save(workerSkill)
        );
    }

    @Override
    @Transactional
    public void removeSkill(
            Long workerId,
            Long workerSkillId) {

        WorkerSkill workerSkill =
                findWorkerSkill(
                        workerId,
                        workerSkillId
                );

        repository.delete(workerSkill);
    }

    private WorkerSkill findWorkerSkill(
            Long workerId,
            Long workerSkillId) {

        WorkerSkill workerSkill =
                repository.findById(workerSkillId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Worker skill not found"
                                ));

        if (!workerSkill.getWorker()
                .getId()
                .equals(workerId)) {

            throw new ResourceNotFoundException(
                    "Worker skill not found"
            );
        }

        return workerSkill;
    }
}
