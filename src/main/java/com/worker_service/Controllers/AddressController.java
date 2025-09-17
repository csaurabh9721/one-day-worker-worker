package com.worker_service.Controllers;

import com.worker_service.dto.AddressDTO;
import com.worker_service.dto.ApiResponse;
import com.worker_service.service.AddressService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workerApi/address")
@AllArgsConstructor
public class AddressController {
    private AddressService addressService;

    @GetMapping("/getAllAddress")
    public ResponseEntity<ApiResponse<List<AddressDTO>>> getAllAddress() {
        List<AddressDTO> workers = addressService.getAddresses();
        ApiResponse<List<AddressDTO>> response = new ApiResponse<>(HttpStatus.OK.value(), workers, "Address fetched successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAddressesByWorkerId/{id}")
    public ResponseEntity<ApiResponse<AddressDTO>> getAddressesByWorkerId(@PathVariable Long workerId) {
        AddressDTO workers = addressService.getAddressesByUserId(workerId);
        ApiResponse<AddressDTO> response = new ApiResponse<>(HttpStatus.OK.value(), workers, "Address fetched successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/saveAddressByWorkerId/{id}")
    public ResponseEntity<ApiResponse<AddressDTO>> saveAddressByWorkerId(@Valid @PathVariable Long id, @Valid @RequestBody AddressDTO dto) {
        AddressDTO address = addressService.saveAddress(id, dto);
        ApiResponse<AddressDTO> response = new ApiResponse<>(HttpStatus.OK.value(), address, "Address saved successfully");
        return ResponseEntity.ok(response);
    }

}
