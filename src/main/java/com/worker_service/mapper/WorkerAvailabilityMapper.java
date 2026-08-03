package com.worker_service.mapper;

import com.worker_service.dto.availability.WorkerAvailabilityRequest;
import com.worker_service.dto.availability.WorkerAvailabilityResponse;
import com.worker_service.entity.WorkerAvailability;
import org.springframework.stereotype.Component;

@Component
public class WorkerAvailabilityMapper {

    public WorkerAvailability toEntity(
            WorkerAvailabilityRequest request) {

        if (request == null) {
            return null;
        }

        WorkerAvailability availability =
                new WorkerAvailability();

        availability.setDayOfWeek(
                request.dayOfWeek()
        );

        availability.setStartTime(
                request.startTime()
        );

        availability.setEndTime(
                request.endTime()
        );

        return availability;
    }

    public WorkerAvailabilityResponse toResponse(
            WorkerAvailability availability) {

        if (availability == null) {
            return null;
        }

        Long workerId = null;

        if (availability.getWorker() != null) {
            workerId =
                    availability.getWorker().getId();
        }

        return new WorkerAvailabilityResponse(
                availability.getId(),
                availability.getDayOfWeek(),
                availability.getStartTime(),
                availability.getEndTime(),
                availability.getActive()
        );
    }

    public void updateEntity(
            WorkerAvailabilityRequest request,
            WorkerAvailability availability) {

        if (request == null || availability == null) {
            return;
        }

        if (request.dayOfWeek() != null) {
            availability.setDayOfWeek(
                    request.dayOfWeek()
            );
        }

        if (request.startTime() != null) {
            availability.setStartTime(
                    request.startTime()
            );
        }

        if (request.endTime() != null) {
            availability.setEndTime(
                    request.endTime()
            );
        }
    }
}