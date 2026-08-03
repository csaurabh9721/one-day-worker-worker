package com.worker_service.dto.servicearea;

import com.worker_service.enums.ServiceAreaType;

import java.math.BigDecimal;

public record WorkerServiceAreaResponse(

        Long id,

        ServiceAreaType areaType,

        String city,

        String state,

        String pincode,

        BigDecimal latitude,

        BigDecimal longitude,

        BigDecimal radiusKm,

        Boolean active
) {
}