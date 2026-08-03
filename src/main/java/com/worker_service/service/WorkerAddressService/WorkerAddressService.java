package com.worker_service.service.WorkerAddressService;

import com.worker_service.dto.address.WorkerAddressCreateRequest;
import com.worker_service.dto.address.WorkerAddressResponse;
import com.worker_service.dto.address.WorkerAddressUpdateRequest;

import java.util.List;

public interface WorkerAddressService {

    WorkerAddressResponse addAddress(
            Long workerId,
            WorkerAddressCreateRequest request
    );

    List<WorkerAddressResponse> getWorkerAddresses(
            Long workerId
    );

    WorkerAddressResponse getAddressById(
            Long workerId,
            Long addressId
    );

    WorkerAddressResponse updateAddress(
            Long workerId,
            Long addressId,
            WorkerAddressUpdateRequest request
    );

    void removeAddress(
            Long workerId,
            Long addressId
    );

    void setPrimaryAddress(
            Long workerId,
            Long addressId
    );
}