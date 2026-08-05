package com.worker_service.mapper;

import com.worker_service.dto.workerservice.WorkerServiceCreateRequest;
import com.worker_service.dto.workerservice.WorkerServiceResponse;
import com.worker_service.dto.workerservice.WorkerServiceUpdateRequest;
import com.worker_service.entity.serviceEntities.WorkerService;
import org.springframework.stereotype.Component;

@Component
public class WorkerServiceMapper {

    public WorkerService toEntity(
            WorkerServiceCreateRequest request) {

        if (request == null) {
            return null;
        }

        return  new WorkerService(

        );
    }

    public WorkerServiceResponse toResponse(
            WorkerService workerService) {

        if (workerService == null) {
            return null;
        }

        Long workerId = null;
        Long serviceId = null;
        String serviceName = null;

        if (workerService.getWorker() != null) {
            workerId =
                    workerService.getWorker().getId();
        }

        if (workerService.getService() != null) {
            serviceId =
                    workerService.getService().getId();

            serviceName =
                    workerService.getService().getName();
        }

        return new WorkerServiceResponse(
                workerService.getId(),
                workerId,
                serviceId,
                serviceName,
                workerService.getPricingType(),
                workerService.getBasePrice(),
                workerService.getHourlyRate(),
                workerService.getDailyRate(),
                workerService.getUnitRate(),
                workerService.getUnit(),
                workerService.getNegotiable(),
                workerService.getActive(),
                workerService.getDescription()
        );
    }

    public void updateEntity(
            WorkerServiceUpdateRequest request,
            WorkerService workerService) {

        if (request == null || workerService == null) {
            return;
        }

        if (request.negotiable() != null) {
            workerService.setNegotiable(
                    request.negotiable()
            );
        }

        if (request.description() != null) {
            workerService.setDescription(
                    request.description()
            );
        }
    }
}