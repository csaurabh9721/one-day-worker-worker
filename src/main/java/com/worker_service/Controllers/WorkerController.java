package com.worker_service.Controllers;

import com.worker_service.dto.ApiResponse;
import com.worker_service.dto.SaveWorkerDto;
import com.worker_service.dto.WorkerReturnDTO;
import com.worker_service.dto.WorkerDetailsDto;
import com.worker_service.service.WorkerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/workerService/worker")
@AllArgsConstructor
public class WorkerController {
    private WorkerService workerService;

    @GetMapping("/getWorkerById/{id}")
    public ResponseEntity<ApiResponse<WorkerReturnDTO>> getWorkerById(@PathVariable Long id) {
        WorkerReturnDTO workers = workerService.getWorkerById(id);
        ApiResponse<WorkerReturnDTO> response = new ApiResponse<>(HttpStatus.OK.value(), workers, "Worker fetched successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getWorkerDetailById/{id}")
    public ResponseEntity<ApiResponse<WorkerDetailsDto>> getWorkerDetailById(@PathVariable Long id) {
        WorkerDetailsDto workers = workerService.getWorkerDetailById(id);
        ApiResponse<WorkerDetailsDto> response = new ApiResponse<>(HttpStatus.OK.value(), workers, "Worker  detail fetched successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllWorkers")
    public ResponseEntity<ApiResponse<List<WorkerReturnDTO>>> getAllWorkers() {
        List<WorkerReturnDTO> workers = workerService.getWorkers();
        ApiResponse<List<WorkerReturnDTO>> response = new ApiResponse<>(HttpStatus.OK.value(), workers, "Worker fetched successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registerWorker")
    public ResponseEntity<ApiResponse<WorkerReturnDTO>> registerWorker(@Valid @RequestBody SaveWorkerDto dto) {
        WorkerReturnDTO workers = workerService.addWorker(dto);
        ApiResponse<WorkerReturnDTO> response = new ApiResponse<>(HttpStatus.CREATED.value(), workers, "Worker saved successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/updateWorker/{id}")
    public ResponseEntity<ApiResponse<WorkerReturnDTO>> updateWorker(@Valid @PathVariable Long id, @RequestBody WorkerReturnDTO dto) {
        WorkerReturnDTO workers = workerService.updateWorker(id, dto);
        ApiResponse<WorkerReturnDTO> response = new ApiResponse<>(HttpStatus.OK.value(), workers, "Worker updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteWorker/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteWorker(@PathVariable Long id) {
        boolean deleted = workerService.deleteWorkerById(id);
        ApiResponse<Boolean> response = new ApiResponse<>(HttpStatus.OK.value(), deleted, "Worker deleted successfully");
        return ResponseEntity.ok(response);
    }


    @PostMapping("/{id}/upload-pic")
    public ResponseEntity<ApiResponse<String>> uploadProfilePic(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        try {
            String url = workerService.saveUserProfilePic(id, file);
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK.value(), "Profile pic uploaded: " + url, "Pic uploaded successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed", "Pic uploaded failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }
}
