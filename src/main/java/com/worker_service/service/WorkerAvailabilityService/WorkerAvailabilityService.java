package com.worker_service.service.WorkerAvailabilityService;

import com.worker_service.dto.availability.WorkerAvailabilityRequest;
import com.worker_service.dto.availability.WorkerAvailabilityResponse;

import java.util.List;

public interface WorkerAvailabilityService {

    WorkerAvailabilityResponse setAvailability(
            Long workerId,
            WorkerAvailabilityRequest request
    );

    List<WorkerAvailabilityResponse> getWorkerAvailability(
            Long workerId
    );

    WorkerAvailabilityResponse updateAvailability(
            Long workerId,
            Long availabilityId,
            WorkerAvailabilityRequest request
    );

    void removeAvailability(
            Long workerId,
            Long availabilityId
    );
}