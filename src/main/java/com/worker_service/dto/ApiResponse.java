package com.worker_service.dto;

public record ApiResponse<T>(int status, T data, String message) {
}
