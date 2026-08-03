package com.worker_service.service.WorkerServiceAreaService;

import com.worker_service.dto.servicearea.WorkerServiceAreaCreateRequest;
import com.worker_service.dto.servicearea.WorkerServiceAreaResponse;
import com.worker_service.dto.servicearea.WorkerServiceAreaUpdateRequest;

import java.util.List;

public interface WorkerServiceAreaService {

    WorkerServiceAreaResponse addServiceArea(
            Long workerId,
            WorkerServiceAreaCreateRequest request
    );

    List<WorkerServiceAreaResponse> getWorkerServiceAreas(
            Long workerId
    );

    WorkerServiceAreaResponse updateServiceArea(
            Long workerId,
            Long serviceAreaId,
            WorkerServiceAreaUpdateRequest request
    );

    void removeServiceArea(
            Long workerId,
            Long serviceAreaId
    );
}