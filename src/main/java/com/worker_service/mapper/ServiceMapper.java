package com.worker_service.mapper;
import com.worker_service.dto.service.ServiceCreateRequest;
import com.worker_service.dto.service.ServiceResponse;
import com.worker_service.dto.service.ServiceUpdateRequest;
import com.worker_service.entity.Service;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper {

    public Service toEntity(
            ServiceCreateRequest request) {

        if (request == null) {
            return null;
        }

        Service service = new Service();

        service.setName(request.name());
        service.setDescription(request.description());

        return service;
    }

    public ServiceResponse toResponse(
            Service service) {

        if (service == null) {
            return null;
        }

        return new ServiceResponse(
                service.getId(),
                service.getName(),
                service.getDescription(),
                service.getCategory().getId(),
                service.getCategory().getName(),
                service.getCategory().getIconUrl(),
                service.getStatus(),
                service.getBookable(),
                service.getWorkerSelectable()
        );
    }

    public void updateEntity(
            ServiceUpdateRequest request,
            Service service) {

        if (request == null || service == null) {
            return;
        }

        if (request.name() != null) {
            service.setName(request.name());
        }

        if (request.description() != null) {
            service.setDescription(
                    request.description()
            );
        }
    }
}