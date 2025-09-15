package com.worker_service.Controllers;

import com.worker_service.dto.AddressDTO;
import com.worker_service.dto.ApiResponse;
import com.worker_service.dto.WorkerDTO;
import com.worker_service.service.AddressService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
@AllArgsConstructor
public class AddressController {
    private AddressService addressService;

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<AddressDTO>>> getAllworkers() {
        List<AddressDTO> workers = addressService.getAddresses();
        ApiResponse<List<AddressDTO>> response = new ApiResponse<>(HttpStatus.OK.value(), workers, "Address fetched successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/save/{id}")
    public ResponseEntity<ApiResponse<WorkerDTO>> addworker(@Valid @PathVariable Long id,@Valid @RequestBody AddressDTO dto) {

        WorkerDTO workers = addressService.saveAddress(id, dto);
        ApiResponse<WorkerDTO> response = new ApiResponse<>(HttpStatus.OK.value(), workers, "Address saved successfully");
        return ResponseEntity.ok(response);
    }

}
