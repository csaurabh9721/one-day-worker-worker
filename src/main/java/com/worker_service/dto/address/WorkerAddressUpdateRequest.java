package com.worker_service.dto.address;

import com.worker_service.enums.AddressType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record WorkerAddressUpdateRequest(

        @NotBlank
        @Size(max = 255)
        String addressLine1,

        @Size(max = 255)
        String addressLine2,

        @NotBlank
        String city,

        @NotBlank
        String state,

        @NotBlank
        String pincode,

        @DecimalMin("-90.0")
        @DecimalMax("90.0")
        BigDecimal latitude,

        @DecimalMin("-180.0")
        @DecimalMax("180.0")
        BigDecimal longitude,

        @NotNull
        AddressType addressType,

        Boolean primaryAddress
) {
}