package com.worker_service.Controllers;


import com.worker_service.dto.SkillPayloadDto;
import com.worker_service.dto.SkillResponseDto;
import com.worker_service.dto.ApiResponse;
import com.worker_service.service.SkillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping("/getSkillsByWorkerId/{workerId}")
    public ResponseEntity<ApiResponse<List<SkillResponseDto>>> getSkillsByWorkerId(@PathVariable Long workerId) {
        List<SkillResponseDto> skills = skillService.getSkillByWorkerId(workerId);
        ApiResponse<List<SkillResponseDto>> response =
                new ApiResponse<>(HttpStatus.OK.value(), skills, "Skills fetched successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addSkill/{workerId}")
    public ResponseEntity<ApiResponse<SkillResponseDto>> addSkill(
            @PathVariable Long workerId,
            @RequestBody SkillPayloadDto dto) {
        SkillResponseDto savedSkill = skillService.saveWorkerSkill(workerId, dto);
        ApiResponse<SkillResponseDto> response =
                new ApiResponse<>(HttpStatus.CREATED.value(), savedSkill, "Skill saved successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/updateSkill/{skillId}")
    public ResponseEntity<ApiResponse<SkillResponseDto>> updateSkill(
            @PathVariable Long skillId,
            @RequestBody SkillPayloadDto dto) {
        SkillResponseDto updatedSkill = skillService.updateWorkerSkill(skillId, dto);
        ApiResponse<SkillResponseDto> response =
                new ApiResponse<>(HttpStatus.OK.value(), updatedSkill, "Skill updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteSkill/{skillId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteSkill(@PathVariable Long skillId) {
        Boolean deleted = skillService.deleteWorkerSkillBySkillId(skillId);
        ApiResponse<Boolean> response =
                new ApiResponse<>(HttpStatus.OK.value(), deleted, "Skill deleted successfully");
        return ResponseEntity.ok(response);
    }
}

