package com.worker_service.service.SkillService;
import com.worker_service.dto.common.PageResponse;
import com.worker_service.dto.skill.SkillCreateRequest;
import com.worker_service.dto.skill.SkillResponse;
import com.worker_service.dto.skill.SkillUpdateRequest;
import com.worker_service.entity.Skill;
import com.worker_service.globleException.DuplicateResourceException;
import com.worker_service.globleException.ResourceNotFoundException;
import com.worker_service.mapper.SkillMapper;
import com.worker_service.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SkillServiceImpl implements SkillService {

    private final SkillRepository repository;
    private final SkillMapper mapper;

    @Override
    @Transactional
    public SkillResponse createSkill(
            SkillCreateRequest request) {

        if (repository.existsByNameIgnoreCase(request.name())) {
            throw new DuplicateResourceException(
                    "Skill already exists: " + request.name()
            );
        }

        Skill skill = mapper.toEntity(request);

        skill.setActive(true);

        return mapper.toResponse(repository.save(skill));
    }

    @Override
    public SkillResponse getSkillById(Long skillId) {

        return mapper.toResponse(
                findSkill(skillId)
        );
    }

    @Override
    public PageResponse<SkillResponse> getSkills(
            Pageable pageable) {

        Page<Skill> page =
                repository.findByActiveTrue(pageable);

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
    public SkillResponse updateSkill(
            Long skillId,
            SkillUpdateRequest request) {

        Skill skill = findSkill(skillId);

        if (!skill.getName().equalsIgnoreCase(request.name())
                && repository.existsByNameIgnoreCase(request.name())) {

            throw new DuplicateResourceException(
                    "Skill already exists: " + request.name()
            );
        }

        mapper.updateEntity(request, skill);

        return mapper.toResponse(repository.save(skill));
    }

    @Override
    @Transactional
    public void deactivateSkill(Long skillId) {

        Skill skill = findSkill(skillId);

        skill.setActive(false);
    }

    private Skill findSkill(Long skillId) {

        return repository.findById(skillId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Skill not found: " + skillId
                        ));
    }
}
