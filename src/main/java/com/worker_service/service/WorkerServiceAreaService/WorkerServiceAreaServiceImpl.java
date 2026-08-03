package com.worker_service.service.WorkerServiceAreaService;
import com.worker_service.dto.servicearea.WorkerServiceAreaCreateRequest;
import com.worker_service.dto.servicearea.WorkerServiceAreaResponse;
import com.worker_service.dto.servicearea.WorkerServiceAreaUpdateRequest;
import com.worker_service.entity.Worker;
import com.worker_service.entity.WorkerServiceArea;
import com.worker_service.globleException.ResourceNotFoundException;
import com.worker_service.mapper.WorkerServiceAreaMapper;
import com.worker_service.repository.WorkerRepository;
import com.worker_service.repository.WorkerServiceAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkerServiceAreaServiceImpl
        implements WorkerServiceAreaService {

    private final WorkerServiceAreaRepository repository;
    private final WorkerRepository workerRepository;
    private final WorkerServiceAreaMapper mapper;

    @Override
    @Transactional
    public WorkerServiceAreaResponse addServiceArea(
            Long workerId,
            WorkerServiceAreaCreateRequest request) {

        Worker worker = findWorker(workerId);

        WorkerServiceArea area =
                mapper.toEntity(request);

        area.setWorker(worker);
        area.setActive(true);

        return mapper.toResponse(
                repository.save(area)
        );
    }

    @Override
    public List<WorkerServiceAreaResponse> getWorkerServiceAreas(
            Long workerId) {

        return repository.findByWorkerIdAndActiveTrue(workerId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public WorkerServiceAreaResponse updateServiceArea(
            Long workerId,
            Long serviceAreaId,
            WorkerServiceAreaUpdateRequest request) {

        WorkerServiceArea area =
                findServiceArea(
                        workerId,
                        serviceAreaId
                );

        mapper.updateEntity(request, area);

        return mapper.toResponse(
                repository.save(area)
        );
    }

    @Override
    @Transactional
    public void removeServiceArea(
            Long workerId,
            Long serviceAreaId) {

        WorkerServiceArea area =
                findServiceArea(
                        workerId,
                        serviceAreaId
                );

        area.setActive(false);
    }

    private Worker findWorker(Long workerId) {

        return workerRepository.findById(workerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Worker not found: " + workerId
                        ));
    }

    private WorkerServiceArea findServiceArea(
            Long workerId,
            Long serviceAreaId) {

        WorkerServiceArea area =
                repository.findById(serviceAreaId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Service area not found"
                                ));

        if (!area.getWorker()
                .getId()
                .equals(workerId)) {

            throw new ResourceNotFoundException(
                    "Service area not found"
            );
        }

        return area;
    }
}