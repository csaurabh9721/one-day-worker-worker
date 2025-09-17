package com.worker_service.dto;

import lombok.*;
import org.springframework.stereotype.Service;

@Data
@AllArgsConstructor
@Getter
@Setter
public class ApiResponse<T> {
    private final int statusCode;
    private final T data;
    private final String message;
}
