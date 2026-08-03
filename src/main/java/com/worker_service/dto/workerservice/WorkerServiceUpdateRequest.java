package com.worker_service.dto.workerservice;

import com.worker_service.enums.PricingType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record WorkerServiceUpdateRequest(

        @NotNull
        PricingType pricingType,

        @DecimalMin(value = "0.0")
        BigDecimal basePrice,

        @DecimalMin(value = "0.0")
        BigDecimal hourlyRate,

        @DecimalMin(value = "0.0")
        BigDecimal dailyRate,

        @DecimalMin(value = "0.0")
        BigDecimal unitRate,

        @Size(max = 50)
        String unit,

        @DecimalMin(value = "0.0")
        BigDecimal minimumPrice,

        Boolean negotiable,

        Boolean active,

        @Size(max = 1000)
        String description
) {
}