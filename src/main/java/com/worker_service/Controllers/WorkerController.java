package com.worker_service.Controllers;

import com.worker_service.dto.ApiResponse;
import com.worker_service.dto.WorkerDTO;
import com.worker_service.service.WorkerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workerApi/worker")
@AllArgsConstructor
public class WorkerController {
    private WorkerService workerService;

    @GetMapping("/getWorkerById/{id}")
    public ResponseEntity<ApiResponse<WorkerDTO>> getWorkerById(@PathVariable Long id) {
        WorkerDTO workers = workerService.getWorkerById(id);
        ApiResponse<WorkerDTO> response = new ApiResponse<>(HttpStatus.OK.value(), workers, "worker fetched successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllWorkers")
    public ResponseEntity<ApiResponse<List<WorkerDTO>>> getAllWorkers() {
        List<WorkerDTO> workers = workerService.getWorkers();
        ApiResponse<List<WorkerDTO>> response = new ApiResponse<>(HttpStatus.OK.value(), workers, "worker fetched successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registerWorker")
    public ResponseEntity<ApiResponse<WorkerDTO>> registerWorker(@Valid @RequestBody WorkerDTO dto) {
        WorkerDTO workers = workerService.addWorker(dto);
        ApiResponse<WorkerDTO> response = new ApiResponse<>(HttpStatus.CREATED.value(), workers, "Worker saved successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/updateWorker/{id}")
    public ResponseEntity<ApiResponse<WorkerDTO>> updateWorker(@Valid @PathVariable Long id, @RequestBody WorkerDTO dto) {
        WorkerDTO workers = workerService.updateWorker(id, dto);
        ApiResponse<WorkerDTO> response = new ApiResponse<>(HttpStatus.OK.value(), workers, "Worker updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteWorker/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteWorker(@PathVariable Long id) {
        boolean deleted = workerService.deleteWorkerById(id);
        ApiResponse<Boolean> response = new ApiResponse<>(HttpStatus.OK.value(), deleted, "Worker deleted successfully");
        return ResponseEntity.ok(response);
    }
}
