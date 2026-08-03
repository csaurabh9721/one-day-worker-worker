package com.worker_service.dto.address;

import com.worker_service.enums.AddressType;

import java.math.BigDecimal;

public record WorkerAddressResponse(

        Long id,

        String addressLine1,

        String addressLine2,

        String city,

        String state,

        String pincode,

        BigDecimal latitude,

        BigDecimal longitude,

        AddressType addressType,

        Boolean primaryAddress
) {
}