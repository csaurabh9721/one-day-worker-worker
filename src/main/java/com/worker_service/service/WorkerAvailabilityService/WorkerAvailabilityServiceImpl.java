package com.worker_service.service.WorkerAvailabilityService;


import com.worker_service.dto.availability.WorkerAvailabilityRequest;
import com.worker_service.dto.availability.WorkerAvailabilityResponse;
import com.worker_service.entity.Worker;
import com.worker_service.entity.WorkerAvailability;
import com.worker_service.globleException.DuplicateResourceException;
import com.worker_service.globleException.ResourceNotFoundException;
import com.worker_service.mapper.WorkerAvailabilityMapper;
import com.worker_service.repository.WorkerAvailabilityRepository;
import com.worker_service.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkerAvailabilityServiceImpl
        implements WorkerAvailabilityService {

    private final WorkerAvailabilityRepository repository;
    private final WorkerRepository workerRepository;
    private final WorkerAvailabilityMapper mapper;

    @Override
    @Transactional
    public WorkerAvailabilityResponse setAvailability(
            Long workerId,
            WorkerAvailabilityRequest request) {

        Worker worker = findWorker(workerId);

        if (repository.existsByWorkerIdAndDayOfWeek(
                workerId,
                request.dayOfWeek())) {

            throw new DuplicateResourceException(
                    "Availability already exists for "
                            + request.dayOfWeek()
            );
        }

        WorkerAvailability availability =
                mapper.toEntity(request);

        availability.setWorker(worker);
        availability.setActive(true);

        validateTimeRange(
                request.startTime(),
                request.endTime()
        );

        return mapper.toResponse(
                repository.save(availability)
        );
    }

    @Override
    public List<WorkerAvailabilityResponse>
    getWorkerAvailability(Long workerId) {

        return repository.findByWorkerIdAndActiveTrue(workerId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public WorkerAvailabilityResponse updateAvailability(
            Long workerId,
            Long availabilityId,
            WorkerAvailabilityRequest request) {

        WorkerAvailability availability =
                findAvailability(
                        workerId,
                        availabilityId
                );

        validateTimeRange(
                request.startTime(),
                request.endTime()
        );

        mapper.updateEntity(request, availability);

        return mapper.toResponse(
                repository.save(availability)
        );
    }

    @Override
    @Transactional
    public void removeAvailability(
            Long workerId,
            Long availabilityId) {

        WorkerAvailability availability =
                findAvailability(
                        workerId,
                        availabilityId
                );

        availability.setActive(false);
    }

    private Worker findWorker(Long workerId) {

        return workerRepository.findById(workerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Worker not found: " + workerId
                        ));
    }

    private WorkerAvailability findAvailability(
            Long workerId,
            Long availabilityId) {

        WorkerAvailability availability =
                repository.findById(availabilityId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Availability not found"
                                ));

        if (!availability.getWorker()
                .getId()
                .equals(workerId)) {

            throw new ResourceNotFoundException(
                    "Availability not found"
            );
        }

        return availability;
    }

    private void validateTimeRange(
            java.time.LocalTime startTime,
            java.time.LocalTime endTime) {

        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException(
                    "Start time must be before end time"
            );
        }
    }
}