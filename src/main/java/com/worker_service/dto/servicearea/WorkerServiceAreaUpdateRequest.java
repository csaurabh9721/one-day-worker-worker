package com.worker_service.dto.servicearea;

import com.worker_service.enums.ServiceAreaType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record WorkerServiceAreaUpdateRequest(

        @NotNull
        ServiceAreaType areaType,

        String city,

        String state,

        String pincode,

        @DecimalMin("-90.0")
        @DecimalMax("90.0")
        BigDecimal latitude,

        @DecimalMin("-180.0")
        @DecimalMax("180.0")
        BigDecimal longitude,

        @DecimalMin("0.1")
        BigDecimal radiusKm,

        Boolean active
) {
}