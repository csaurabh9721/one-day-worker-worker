package com.worker_service.serviceImpl;

import com.worker_service.dto.UsersDTO;
import com.worker_service.service.UserFeignService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class UserClientService {
    private final UserFeignService userFeignService;


    /// Services communicate using ""Feign Client""
    ///  /// Higher Recommended
    /// /// Commonly use in modern Spring boot
    ///  /// Less boilerplate code, required dependency to use it
    @CircuitBreaker(
            name = "User-Service",
            fallbackMethod = "userServiceFallback"
    )
    public UsersDTO getUser(Long userId) {

        return userFeignService.users(userId.toString()).getData();
    }

    public UsersDTO userServiceFallback(Long userId, Throwable ex) {
        log.error("User service DOWN for userId {}", userId, ex);
        return null;
    }
}
