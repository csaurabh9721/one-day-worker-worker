package com.worker_service.dto.workerservice;

import com.worker_service.enums.PricingType;

import java.math.BigDecimal;

public record WorkerServiceResponse(

        Long id,

        Long workerId,

        Long serviceId,

        String serviceName,

        PricingType pricingType,

        BigDecimal basePrice,

        BigDecimal hourlyRate,

        BigDecimal dailyRate,

        BigDecimal unitRate,

        String unit,

        Boolean negotiable,

        Boolean active,

        String description
) {
}