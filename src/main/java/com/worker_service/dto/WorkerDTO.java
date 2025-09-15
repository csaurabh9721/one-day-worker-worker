package com.worker_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class WorkerDTO {
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be 3–50 characters")
    private String name;

    @Min(value = 18, message = "Age must be at least 18")
    private Integer age;

    private String photo;

    @NotBlank(message = "Mobile number is required")
    @Size(min = 5, max = 20, message = "Mobile must be 5–20 characters")
    private String mobile;

    private AddressDTO address;
}
