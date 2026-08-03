package com.worker_service.service.ServiceRequestService;

import com.worker_service.dto.common.PageResponse;
import com.worker_service.dto.servicerequest.ServiceRequestCreateRequest;
import com.worker_service.dto.servicerequest.ServiceRequestResponse;
import com.worker_service.dto.servicerequest.ServiceRequestReviewRequest;
import org.springframework.data.domain.Pageable;

public interface ServiceRequestService {

    ServiceRequestResponse createRequest(
            Long workerId,
            ServiceRequestCreateRequest request
    );

    ServiceRequestResponse getRequestById(
            Long requestId
    );

    PageResponse<ServiceRequestResponse> getWorkerRequests(
            Long workerId,
            Pageable pageable
    );

    ServiceRequestResponse reviewRequest(
            Long requestId,
            Long reviewerId,
            ServiceRequestReviewRequest request
    );
}