package com.worker_service.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SaveWorkerDto {
    @NotBlank(message = "Password is required")
    @Size(min = 3, max = 50, message = "Password must be 3–50 characters")
    private String password;

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be 3– 50 characters")
    private String name;

    @NotBlank(message = "Mobile number is required")
    @Size(min = 5, max = 20, message = "Mobile must be 5–20 characters")
    private String mobile;
}
