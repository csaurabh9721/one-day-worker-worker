package com.worker_service.service.WorkerService;

import com.worker_service.dto.common.PageResponse;
import com.worker_service.dto.worker.WorkerCreateRequest;
import com.worker_service.dto.worker.WorkerResponse;
import com.worker_service.dto.worker.WorkerSummaryResponse;
import com.worker_service.dto.worker.WorkerUpdateRequest;
import org.springframework.data.domain.Pageable;

public interface WorkerService {

    WorkerResponse createWorker(
            WorkerCreateRequest request,
            Long identityId
    );

    WorkerResponse getWorkerById(
            Long workerId
    );

    WorkerResponse getMyWorkerProfile(
            Long identityId
    );

    WorkerResponse updateWorker(
            Long workerId,
            Long identityId,
            WorkerUpdateRequest request
    );

    void deactivateWorker(
            Long workerId,
            Long identityId
    );

    PageResponse<WorkerSummaryResponse> searchWorkers(
            String keyword,
            Pageable pageable
    );
}