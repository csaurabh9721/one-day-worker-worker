package com.worker_service.dto.workerservice;

import com.worker_service.enums.PricingType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record WorkerServiceCreateRequest(

        @NotNull
        Long serviceId,

        @NotNull
        PricingType pricingType,

        @DecimalMin(value = "0.0", inclusive = true)
        BigDecimal basePrice,

        @DecimalMin(value = "0.0", inclusive = true)
        BigDecimal hourlyRate,

        @DecimalMin(value = "0.0", inclusive = true)
        BigDecimal dailyRate,

        @DecimalMin(value = "0.0", inclusive = true)
        BigDecimal unitRate,

        @Size(max = 50)
        String unit,

        @DecimalMin(value = "0.0", inclusive = true)
        BigDecimal minimumPrice,

        Boolean negotiable,

        @Size(max = 1000)
        String description
) {
}