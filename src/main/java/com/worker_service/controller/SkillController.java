package com.worker_service.controller;

import com.worker_service.dto.common.PageResponse;
import com.worker_service.dto.skill.SkillCreateRequest;
import com.worker_service.dto.skill.SkillResponse;
import com.worker_service.dto.skill.SkillUpdateRequest;
import com.worker_service.service.SkillService.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @PostMapping
    public ResponseEntity<SkillResponse> create(
            @Valid @RequestBody SkillCreateRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(skillService.createSkill(request));
    }

    @GetMapping("/{skillId}")
    public ResponseEntity<SkillResponse> getById(
            @PathVariable Long skillId) {

        return ResponseEntity.ok(
                skillService.getSkillById(skillId)
        );
    }

    @GetMapping
    public ResponseEntity<PageResponse<SkillResponse>>
    getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        return ResponseEntity.ok(
                skillService.getSkills(pageable)
        );
    }

    @PutMapping("/{skillId}")
    public ResponseEntity<SkillResponse> update(
            @PathVariable Long skillId,
            @Valid @RequestBody SkillUpdateRequest request) {

        return ResponseEntity.ok(
                skillService.updateSkill(
                        skillId,
                        request
                )
        );
    }

    @DeleteMapping("/{skillId}")
    public ResponseEntity<Void> deactivate(
            @PathVariable Long skillId) {

        skillService.deactivateSkill(skillId);

        return ResponseEntity.noContent().build();
    }
}