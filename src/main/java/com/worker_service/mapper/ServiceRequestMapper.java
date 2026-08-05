package com.worker_service.mapper;

import com.worker_service.dto.servicerequest.ServiceRequestCreateRequest;
import com.worker_service.dto.servicerequest.ServiceRequestResponse;
import com.worker_service.entity.serviceEntities.ServiceRequest;
import org.springframework.stereotype.Component;

@Component
public class ServiceRequestMapper {

    public ServiceRequest toEntity(
            ServiceRequestCreateRequest request) {

        if (request == null) {
            return null;
        }

        ServiceRequest serviceRequest =
                new ServiceRequest();

        serviceRequest.setRequestedName(
                request.requestedName()
        );

        serviceRequest.setDescription(
                request.description()
        );

        return serviceRequest;
    }

    public ServiceRequestResponse toResponse(
            ServiceRequest request) {

        if (request == null) {
            return null;
        }

        return new ServiceRequestResponse(
                request.getId(),
                request.getWorker().getId(),
                request.getRequestedName(),
                request.getDescription(),
                request.getSuggestedCategory().getId(),
                request.getStatus(),
                request.getCreatedService().getId(),
                request.getReviewedBy(),
                request.getReviewComment(),
                request.getCreatedAt(),
                request.getUpdatedAt()
        );
    }
}