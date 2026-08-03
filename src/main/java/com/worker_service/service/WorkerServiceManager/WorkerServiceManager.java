package com.worker_service.service.WorkerServiceManager;

import com.worker_service.dto.common.PageResponse;
import com.worker_service.dto.workerservice.WorkerServiceCreateRequest;
import com.worker_service.dto.workerservice.WorkerServiceResponse;
import com.worker_service.dto.workerservice.WorkerServiceUpdateRequest;
import org.springframework.data.domain.Pageable;

public interface WorkerServiceManager {

    WorkerServiceResponse addService(
            Long workerId,
            WorkerServiceCreateRequest request
    );

    WorkerServiceResponse getWorkerService(
            Long workerId,
            Long workerServiceId
    );

    PageResponse<WorkerServiceResponse> getWorkerServices(
            Long workerId,
            Pageable pageable
    );

    WorkerServiceResponse updateService(
            Long workerId,
            Long workerServiceId,
            WorkerServiceUpdateRequest request
    );

    void removeService(
            Long workerId,
            Long workerServiceId
    );

    PageResponse<WorkerServiceResponse> findWorkersByService(
            Long serviceId,
            Pageable pageable
    );
}

/*
worker-service/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── yourcompany/
│   │   │           └── workerservice/
│   │   │               ├── WorkerServiceApplication.java
│   │   │               ├── config/
│   │   │               │   ├── JpaConfig.java
│   │   │               │   ├── SecurityConfig.java
│   │   │               │   ├── RedisConfig.java
│   │   │               │   ├── RabbitMQConfig.java
│   │   │               │   ├── OpenApiConfig.java
│   │   │               │   └── FeignConfig.java
│   │   │               ├── controller/
│   │   │               ├── service/
│   │   │               ├── repository/
│   │   │               ├── entity/
│   │   │               ├── dto/
│   │   │               ├── mapper/
│   │   │               ├── exception/
│   │   │               ├── security/
│   │   │               │   ├── JwtAuthenticationFilter.java
│   │   │               │   ├── JwtTokenUtil.java
│   │   │               │   ├── AuthenticatedUser.java
│   │   │               │   └── SecurityConstants.java
│   │   │               ├── client/
│   │   │               │   ├── AuthServiceClient.java
│   │   │               │   └── CustomerServiceClient.java
│   │   │               ├── event/
│   │   │               │   ├── WorkerRegisteredEvent.java
│   │   │               │   ├── WorkerUpdatedEvent.java
│   │   │               │   ├── WorkerStatusChangedEvent.java
│   │   │               │   └── WorkerEventPublisher.java
│   │   │               ├── messaging/
│   │   │               │   ├── RabbitMQPublisher.java
│   │   │               │   └── RabbitMQConstants.java
│   │   │               ├── enums/
│   │   │               │
│   │   │               └── util/
│   │   │                   ├── SecurityUtil.java
│   │   │                   └── ValidationUtil.java
│   │   │
│   │   └── resources/
│   │       └── db/
│   │           └── migration/
*/