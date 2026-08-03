package com.worker_service.mapper;

import com.worker_service.dto.address.WorkerAddressCreateRequest;
import com.worker_service.dto.address.WorkerAddressResponse;
import com.worker_service.dto.address.WorkerAddressUpdateRequest;
import com.worker_service.entity.WorkerAddress;
import org.springframework.stereotype.Component;

@Component
public class WorkerAddressMapper {

    public WorkerAddress toEntity(
            WorkerAddressCreateRequest request) {

        if (request == null) {
            return null;
        }

        WorkerAddress address =
                new WorkerAddress();

        address.setAddressLine1(
                request.addressLine1()
        );

        address.setAddressLine2(
                request.addressLine2()
        );

        address.setCity(
                request.city()
        );

        address.setState(
                request.state()
        );


        address.setPincode(
                request.pincode()
        );

        address.setLatitude(
                request.latitude()
        );

        address.setLongitude(
                request.longitude()
        );

        return address;
    }

    public WorkerAddressResponse toResponse(
            WorkerAddress address) {

        if (address == null) {
            return null;
        }

        Long workerId = null;

        if (address.getWorker() != null) {
            workerId =
                    address.getWorker().getId();
        }

        return new WorkerAddressResponse(
                address.getId(),
                address.getAddressLine1(),
                address.getAddressLine2(),
                address.getCity(),
                address.getState(),
                address.getPincode(),
                address.getLatitude(),
                address.getLongitude(),
                address.getAddressType(),
                address.getPrimaryAddress()
        );
    }

    public void updateEntity(
            WorkerAddressUpdateRequest request,
            WorkerAddress address) {

        if (request == null || address == null) {
            return;
        }

        if (request.addressLine1() != null) {
            address.setAddressLine1(
                    request.addressLine1()
            );
        }

        if (request.addressLine2() != null) {
            address.setAddressLine2(
                    request.addressLine2()
            );
        }

        if (request.city() != null) {
            address.setCity(
                    request.city()
            );
        }

        if (request.state() != null) {
            address.setState(
                    request.state()
            );
        }

        if (request.pincode() != null) {
            address.setPincode(
                    request.pincode()
            );
        }

        if (request.latitude() != null) {
            address.setLatitude(
                    request.latitude()
            );
        }

        if (request.longitude() != null) {
            address.setLongitude(
                    request.longitude()
            );
        }
    }
}