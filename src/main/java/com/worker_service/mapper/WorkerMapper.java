package com.worker_service.mapper;

import com.worker_service.dto.worker.WorkerCreateRequest;
import com.worker_service.dto.worker.WorkerResponse;
import com.worker_service.dto.worker.WorkerSummaryResponse;
import com.worker_service.dto.worker.WorkerUpdateRequest;
import com.worker_service.entity.Worker;
import org.springframework.stereotype.Component;

@Component
public class WorkerMapper {

    public Worker toEntity(WorkerCreateRequest request) {

        if (request == null) {
            return null;
        }

        Worker worker = new Worker();
        worker.setIdentityId(request.identityId());
        worker.setFirstName(request.firstName());
        worker.setLastName(request.lastName());
        worker.setPhone(request.phone());

        return worker;
    }

    public WorkerResponse toResponse(Worker worker) {

        if (worker == null) {
            return null;
        }
        return new WorkerResponse(
                worker.getId(),
                worker.getIdentityId(),
                worker.getFirstName(),
                worker.getLastName(),
                worker.getPhone(),
                worker.getEmail(),
                worker.getProfileImageUrl(),
                worker.getGender(),
                worker.getDateOfBirth(),
                worker.getStatus(),
                worker.getAvailabilityStatus(),
                worker.getExperienceYears(),
                worker.getAverageRating(),
                worker.getTotalCompletedJobs(),
                worker.getProfileVerified(),
                worker.getActive(),
                worker.getCreatedAt(),
                worker.getUpdatedAt()
        );
    }

    public WorkerSummaryResponse toSummaryResponse(
            Worker worker) {

        if (worker == null) {
            return null;
        }

        return new WorkerSummaryResponse(
                worker.getId(),
                worker.getFirstName(),
                worker.getLastName(),
                worker.getProfileImageUrl(),
                worker.getExperienceYears(),
                worker.getAverageRating(),
                worker.getTotalCompletedJobs(),
                worker.getAvailabilityStatus()
        );
    }

    public void updateEntity(
            WorkerUpdateRequest request,
            Worker worker) {

        if (request == null || worker == null) {
            return;
        }

        if (request.firstName() != null) {
            worker.setFirstName(request.firstName());
        }

        if (request.lastName() != null) {
            worker.setLastName(request.lastName());
        }

        if (request.email() != null) {
            worker.setEmail(request.email());
        }

        if (request.phone() != null) {
            worker.setPhone(request.phone());
        }
    }
}