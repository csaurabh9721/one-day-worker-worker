package com.worker_service.service.WorkerService;
import com.worker_service.dto.common.PageResponse;
import com.worker_service.dto.worker.WorkerCreateRequest;
import com.worker_service.dto.worker.WorkerResponse;
import com.worker_service.dto.worker.WorkerSummaryResponse;
import com.worker_service.dto.worker.WorkerUpdateRequest;
import com.worker_service.entity.Worker;
import com.worker_service.enums.AvailabilityStatus;
import com.worker_service.enums.WorkerStatus;
import com.worker_service.globleException.DuplicateResourceException;
import com.worker_service.globleException.ResourceNotFoundException;
import com.worker_service.mapper.WorkerMapper;
import com.worker_service.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository repository;
    private final WorkerMapper mapper;

    @Override
    @Transactional
    public WorkerResponse createWorker(
            WorkerCreateRequest request,
            Long identityId) {

        if (repository.existsByIdentityId(identityId)) {
            throw new DuplicateResourceException(
                    "Worker profile already exists for identity: "
                            + identityId
            );
        }

        if (request.email() != null
                && repository.existsByEmailIgnoreCase(request.email())) {

            throw new DuplicateResourceException(
                    "Email already exists"
            );
        }

        if (request.phone() != null
                && repository.existsByPhone(request.phone())) {

            throw new DuplicateResourceException(
                    "Phone already exists"
            );
        }

        Worker worker = mapper.toEntity(request);

        worker.setIdentityId(identityId);
        worker.setStatus(WorkerStatus.PENDING_VERIFICATION);
        worker.setAvailabilityStatus(AvailabilityStatus.OFFLINE);
        worker.setActive(true);
        worker.setProfileVerified(false);
        worker.setTotalCompletedJobs(0);

        Worker saved = repository.save(worker);

        return mapper.toResponse(saved);
    }

    @Override
    public WorkerResponse getWorkerById(Long workerId) {

        Worker worker = findWorker(workerId);

        return mapper.toResponse(worker);
    }

    @Override
    public WorkerResponse getMyWorkerProfile(
            Long identityId) {

        Worker worker = repository.findByIdentityId(identityId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Worker profile not found"
                        ));

        return mapper.toResponse(worker);
    }

    @Override
    @Transactional
    public WorkerResponse updateWorker(
            Long workerId,
            Long identityId,
            WorkerUpdateRequest request) {

        Worker worker = findWorker(workerId);

        verifyOwnership(worker, identityId);

        if (request.email() != null
                && !request.email().equalsIgnoreCase(worker.getEmail())
                && repository.existsByEmailIgnoreCase(request.email())) {

            throw new DuplicateResourceException(
                    "Email already exists"
            );
        }

        if (request.phone() != null
                && !request.phone().equals(worker.getPhone())
                && repository.existsByPhone(request.phone())) {

            throw new DuplicateResourceException(
                    "Phone already exists"
            );
        }

        mapper.updateEntity(request, worker);

        return mapper.toResponse(repository.save(worker));
    }

    @Override
    @Transactional
    public void deactivateWorker(
            Long workerId,
            Long identityId) {

        Worker worker = findWorker(workerId);

        verifyOwnership(worker, identityId);

        worker.setActive(false);
        worker.setStatus(WorkerStatus.INACTIVE);
        worker.setAvailabilityStatus(
                AvailabilityStatus.OFFLINE
        );
    }

    @Override
    public PageResponse<WorkerSummaryResponse> searchWorkers(
            String keyword,
            Pageable pageable) {

        Page<Worker> page;

        if (keyword == null || keyword.isBlank()) {

            page = repository.findByStatus(
                    WorkerStatus.ACTIVE,
                    pageable
            );

        } else {

            page = repository.searchWorkers(
                    keyword.trim(),
                    pageable
            );
        }

        return new PageResponse<>(
                page.getContent()
                        .stream()
                        .map(mapper::toSummaryResponse)
                        .toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }

    private Worker findWorker(Long workerId) {

        return repository.findById(workerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Worker not found: " + workerId
                        ));
    }

    private void verifyOwnership(
            Worker worker,
            Long identityId) {

        if (!worker.getIdentityId().equals(identityId)) {

            throw new ResourceNotFoundException(
                    "Worker profile not found"
            );
        }
    }
}
