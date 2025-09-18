package com.worker_service.service;

import com.worker_service.dto.ApiResponse;
import com.worker_service.dto.UsersDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USERS-SERVICE")
public interface UserFeignService {

    @GetMapping("/userApi/user/getUserById/{id}")
    ApiResponse<UsersDTO> users(@PathVariable String id);
}
