package com.worker_service.dto.subcategory;

import com.worker_service.enums.ServiceStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceSubcategoryResponse {

    private Long id;

    private String name;

    private String description;

    private String iconUrl;

    private ServiceStatus status;

    private Long categoryId;

    private String categoryName;
}