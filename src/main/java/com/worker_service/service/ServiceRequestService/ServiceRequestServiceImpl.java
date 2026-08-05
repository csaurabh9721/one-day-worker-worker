package com.worker_service.service.ServiceRequestService;


import com.worker_service.dto.common.PageResponse;
import com.worker_service.dto.servicerequest.ServiceRequestCreateRequest;
import com.worker_service.dto.servicerequest.ServiceRequestResponse;
import com.worker_service.dto.servicerequest.ServiceRequestReviewRequest;
import com.worker_service.entity.serviceEntities.ServiceRequest;
import com.worker_service.entity.Worker;
import com.worker_service.enums.ServiceRequestStatus;
import com.worker_service.globleException.DuplicateResourceException;
import com.worker_service.globleException.ResourceNotFoundException;
import com.worker_service.mapper.ServiceRequestMapper;
import com.worker_service.repository.ServiceRequestRepository;
import com.worker_service.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServiceRequestServiceImpl
        implements ServiceRequestService {

    private final ServiceRequestRepository repository;
    private final WorkerRepository workerRepository;
    private final ServiceRequestMapper mapper;

    @Override
    @Transactional
    public ServiceRequestResponse createRequest(
            Long workerId,
            ServiceRequestCreateRequest request) {

      Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Worker not found: " + workerId
                        ));

        if (repository
                .existsByWorkerIdAndRequestedNameIgnoreCaseAndStatus(
                        workerId,
                        request.requestedName(),
                        ServiceRequestStatus.PENDING
                )) {

            throw new DuplicateResourceException(
                    "A request for this service is already pending"
            );
        }

        ServiceRequest serviceRequest =
                mapper.toEntity(request);

        serviceRequest.setWorker(worker);
        serviceRequest.setStatus(
                ServiceRequestStatus.PENDING
        );

        return mapper.toResponse(
                repository.save(serviceRequest)
        );
    }

    @Override
    public ServiceRequestResponse getRequestById(
            Long requestId) {

        return mapper.toResponse(
                findRequest(requestId)
        );
    }

    @Override
    public PageResponse<ServiceRequestResponse> getWorkerRequests(
            Long workerId,
            Pageable pageable) {

        Page<ServiceRequest> page =
                repository.findByWorkerId(
                        workerId,
                        pageable
                );

        return new PageResponse<>(
                page.getContent()
                        .stream()
                        .map(mapper::toResponse)
                        .toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }

    @Override
    @Transactional
    public ServiceRequestResponse reviewRequest(
            Long requestId,
            Long reviewerId,
            ServiceRequestReviewRequest request) {

        ServiceRequest serviceRequest =
                findRequest(requestId);

        if (serviceRequest.getStatus()
                != ServiceRequestStatus.PENDING) {

            throw new IllegalStateException(
                    "Only pending requests can be reviewed"
            );
        }

        serviceRequest.setStatus(request.status());

        /*
         * These fields depend on your ServiceRequest entity.
         */
        // serviceRequest.setReviewedBy(reviewerId);
        // serviceRequest.setReviewComment(request.reviewComment());

        return mapper.toResponse(
                repository.save(serviceRequest)
        );
    }

    private ServiceRequest findRequest(Long requestId) {

        return repository.findById(requestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Service request not found: "
                                        + requestId
                        ));
    }
}