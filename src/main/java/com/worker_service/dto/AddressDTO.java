package com.worker_service.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddressDTO {
    private Long id;
    @NotBlank(message = "Street name is required")
    private String streetName;

    @NotBlank(message = "Locality is required")
    private String locality;

    @NotBlank(message = "Sub-locality is required")
    private String sublocality;

    @NotBlank(message = "City is required")
    @Size(min = 1, max = 50, message = "City length must be between 1 and 50")
    private String city;

    @Size(min = 1, max = 50, message = "State length must be between 1 and 50")
    private String state;

    @Size(min = 1, max = 50, message = "Country length must be between 1 and 50")
    private String country;

    @Min(value = 100000, message = "PinCode must be at least 6 digits")
    @Max(value = 999999, message = "PinCode cannot exceed 6 digits")
    private Integer pinCode;
}