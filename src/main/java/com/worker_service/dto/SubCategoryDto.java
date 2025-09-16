package com.worker_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubCategoryDto {
    private Long id;
    @NotBlank(message = "Name can not be empty.")
    private String name;
    @NotNull(message = "Category id must be required")
    private Long categoryId;
}
