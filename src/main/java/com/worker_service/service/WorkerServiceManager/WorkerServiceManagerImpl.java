package com.worker_service.service.WorkerServiceManager;


import com.worker_service.dto.common.PageResponse;
import com.worker_service.dto.workerservice.WorkerServiceCreateRequest;
import com.worker_service.dto.workerservice.WorkerServiceResponse;
import com.worker_service.dto.workerservice.WorkerServiceUpdateRequest;
import com.worker_service.entity.Service;
import com.worker_service.entity.Worker;
import com.worker_service.entity.WorkerService;
import com.worker_service.globleException.DuplicateResourceException;
import com.worker_service.globleException.ResourceNotFoundException;
import com.worker_service.mapper.WorkerServiceMapper;
import com.worker_service.repository.ServiceRepository;
import com.worker_service.repository.WorkerRepository;
import com.worker_service.repository.WorkerServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkerServiceManagerImpl
        implements WorkerServiceManager {

    private final WorkerServiceRepository repository;
    private final WorkerRepository workerRepository;
    private final ServiceRepository serviceRepository;
    private final WorkerServiceMapper mapper;

    @Override
    @Transactional
    public WorkerServiceResponse addService(
            Long workerId,
            WorkerServiceCreateRequest request) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Worker not found: " + workerId
                        ));

        Service service = serviceRepository.findById(
                        request.serviceId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Service not found: "
                                        + request.serviceId()
                        ));

        if (repository.existsByWorkerIdAndServiceId(
                workerId,
                request.serviceId())) {

            throw new DuplicateResourceException(
                    "Worker already provides this service"
            );
        }

        WorkerService workerService =
                mapper.toEntity(request);

        workerService.setWorker(worker);
        workerService.setService(service);
        workerService.setActive(true);

        WorkerService saved =
                repository.save(workerService);

        return mapper.toResponse(saved);
    }

    @Override
    public WorkerServiceResponse getWorkerService(
            Long workerId,
            Long workerServiceId) {

        WorkerService workerService =
                findWorkerService(
                        workerId,
                        workerServiceId
                );

        return mapper.toResponse(workerService);
    }

    @Override
    public PageResponse<WorkerServiceResponse> getWorkerServices(
            Long workerId,
            Pageable pageable) {

        Page<WorkerService> page =
                repository.findByWorkerIdAndActiveTrue(
                        workerId,
                        pageable
                );

        return toPageResponse(page);
    }

    @Override
    @Transactional
    public WorkerServiceResponse updateService(
            Long workerId,
            Long workerServiceId,
            WorkerServiceUpdateRequest request) {

        WorkerService workerService =
                findWorkerService(
                        workerId,
                        workerServiceId
                );

        mapper.updateEntity(request, workerService);

        return mapper.toResponse(
                repository.save(workerService)
        );
    }

    @Override
    @Transactional
    public void removeService(
            Long workerId,
            Long workerServiceId) {

        WorkerService workerService =
                findWorkerService(
                        workerId,
                        workerServiceId
                );

        workerService.setActive(false);
    }

    @Override
    public PageResponse<WorkerServiceResponse> findWorkersByService(
            Long serviceId,
            Pageable pageable) {

        Page<WorkerService> page =
                repository.findActiveWorkersByService(
                        serviceId,
                        pageable
                );

        return toPageResponse(page);
    }

    private WorkerService findWorkerService(
            Long workerId,
            Long workerServiceId) {

        WorkerService workerService =
                repository.findById(workerServiceId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Worker service not found: "
                                                + workerServiceId
                                ));

        if (!workerService.getWorker()
                .getId()
                .equals(workerId)) {

            throw new ResourceNotFoundException(
                    "Worker service not found"
            );
        }

        return workerService;
    }

    private PageResponse<WorkerServiceResponse> toPageResponse(
            Page<WorkerService> page) {

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
}
