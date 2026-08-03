package com.worker_service.mapper;

import com.worker_service.dto.servicearea.WorkerServiceAreaCreateRequest;
import com.worker_service.dto.servicearea.WorkerServiceAreaResponse;
import com.worker_service.dto.servicearea.WorkerServiceAreaUpdateRequest;
import com.worker_service.entity.WorkerServiceArea;
import org.springframework.stereotype.Component;

@Component
public class WorkerServiceAreaMapper {

    public WorkerServiceArea toEntity(
            WorkerServiceAreaCreateRequest request) {

        if (request == null) {
            return null;
        }

        WorkerServiceArea area =
                new WorkerServiceArea();

        area.setCity(
                request.city()
        );

        area.setState(
                request.state()
        );

        area.setPincode(
                request.pincode()
        );

        area.setRadiusKm(
                request.radiusKm()
        );

        return area;
    }

    public WorkerServiceAreaResponse toResponse(
            WorkerServiceArea area) {

        if (area == null) {
            return null;
        }

        Long workerId = null;

        if (area.getWorker() != null) {
            workerId =
                    area.getWorker().getId();
        }

        return new WorkerServiceAreaResponse(
                area.getId(),
                area.getAreaType(),
                area.getCity(),
                area.getState(),
                area.getPincode(),
                area.getLatitude(),
                area.getLongitude(),
                area.getRadiusKm(),
                area.getActive()
        );
    }

    public void updateEntity(
            WorkerServiceAreaUpdateRequest request,
            WorkerServiceArea area) {

        if (request == null || area == null) {
            return;
        }

        if (request.city() != null) {
            area.setCity(
                    request.city()
            );
        }

        if (request.state() != null) {
            area.setState(
                    request.state()
            );
        }

        if (request.pincode() != null) {
            area.setPincode(
                    request.pincode()
            );
        }

        if (request.radiusKm() != null) {
            area.setRadiusKm(
                    request.radiusKm()
            );
        }
    }
}