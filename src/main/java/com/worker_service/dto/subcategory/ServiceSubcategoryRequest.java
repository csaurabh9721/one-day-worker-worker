package com.worker_service.dto.subcategory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceSubcategoryRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    @Size(max = 500)
    private String iconUrl;

    @NotNull(message = "Category Id is required")
    private Long categoryId;

}