package com.worker_service.service.WorkerAddressService;


import com.worker_service.dto.address.WorkerAddressCreateRequest;
import com.worker_service.dto.address.WorkerAddressResponse;
import com.worker_service.dto.address.WorkerAddressUpdateRequest;
import com.worker_service.entity.Worker;
import com.worker_service.entity.WorkerAddress;
import com.worker_service.globleException.ResourceNotFoundException;
import com.worker_service.mapper.WorkerAddressMapper;
import com.worker_service.repository.WorkerAddressRepository;
import com.worker_service.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkerAddressServiceImpl
        implements WorkerAddressService {

    private final WorkerAddressRepository repository;
    private final WorkerRepository workerRepository;
    private final WorkerAddressMapper mapper;

    @Override
    @Transactional
    public WorkerAddressResponse addAddress(
            Long workerId,
            WorkerAddressCreateRequest request) {

        Worker worker = findWorker(workerId);

        WorkerAddress address =
                mapper.toEntity(request);

        address.setWorker(worker);

        if (Boolean.TRUE.equals(request.primaryAddress())) {
            clearPrimaryAddress(workerId);
        }

        address.setPrimaryAddress(
                Boolean.TRUE.equals(request.primaryAddress())
        );

        return mapper.toResponse(
                repository.save(address)
        );
    }

    @Override
    public List<WorkerAddressResponse> getWorkerAddresses(
            Long workerId) {

        return repository.findByWorkerId(workerId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public WorkerAddressResponse getAddressById(
            Long workerId,
            Long addressId) {

        return mapper.toResponse(
                findAddress(workerId, addressId)
        );
    }

    @Override
    @Transactional
    public WorkerAddressResponse updateAddress(
            Long workerId,
            Long addressId,
            WorkerAddressUpdateRequest request) {

        WorkerAddress address =
                findAddress(workerId, addressId);

        if (Boolean.TRUE.equals(request.primaryAddress())) {
            clearPrimaryAddress(workerId);
        }

        mapper.updateEntity(request, address);

        return mapper.toResponse(
                repository.save(address)
        );
    }

    @Override
    @Transactional
    public void removeAddress(
            Long workerId,
            Long addressId) {

        WorkerAddress address =
                findAddress(workerId, addressId);

        repository.delete(address);
    }

    @Override
    @Transactional
    public void setPrimaryAddress(
            Long workerId,
            Long addressId) {

        WorkerAddress address =
                findAddress(workerId, addressId);

        clearPrimaryAddress(workerId);

        address.setPrimaryAddress(true);
    }

    private Worker findWorker(Long workerId) {

        return workerRepository.findById(workerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Worker not found: " + workerId
                        ));
    }

    private WorkerAddress findAddress(
            Long workerId,
            Long addressId) {

        WorkerAddress address =
                repository.findById(addressId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Address not found: "
                                                + addressId
                                ));

        if (!address.getWorker()
                .getId()
                .equals(workerId)) {

            throw new ResourceNotFoundException(
                    "Address not found"
            );
        }

        return address;
    }

    private void clearPrimaryAddress(Long workerId) {

        repository.findByWorkerId(workerId)
                .forEach(address ->
                        address.setPrimaryAddress(false)
                );
    }
}