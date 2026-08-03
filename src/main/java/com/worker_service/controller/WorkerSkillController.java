package com.worker_service.controller;

import com.worker_service.dto.common.PageResponse;
import com.worker_service.dto.skill.WorkerSkillResponse;
import com.worker_service.service.WorkerSkillService.WorkerSkillService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/workers/{workerId}/skills")
@RequiredArgsConstructor
public class WorkerSkillController {

    private final WorkerSkillService service;

    @PostMapping("/{skillId}")
    public ResponseEntity<WorkerSkillResponse> addSkill(
            @PathVariable Long workerId,
            @PathVariable Long skillId,
            @RequestParam(defaultValue = "0")
            @Min(0) Integer experienceYears) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        service.addSkill(
                                workerId,
                                skillId,
                                experienceYears
                        )
                );
    }

    @GetMapping
    public ResponseEntity<PageResponse<WorkerSkillResponse>>
    getSkills(
            @PathVariable Long workerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        return ResponseEntity.ok(
                service.getWorkerSkills(
                        workerId,
                        pageable
                )
        );
    }

    @PutMapping("/{workerSkillId}")
    public ResponseEntity<WorkerSkillResponse> updateSkill(
            @PathVariable Long workerId,
            @PathVariable Long workerSkillId,
            @RequestParam @Min(0) Integer experienceYears) {

        return ResponseEntity.ok(
                service.updateSkill(
                        workerId,
                        workerSkillId,
                        experienceYears
                )
        );
    }

    @DeleteMapping("/{workerSkillId}")
    public ResponseEntity<Void> removeSkill(
            @PathVariable Long workerId,
            @PathVariable Long workerSkillId) {

        service.removeSkill(
                workerId,
                workerSkillId
        );

        return ResponseEntity.noContent().build();
    }
}