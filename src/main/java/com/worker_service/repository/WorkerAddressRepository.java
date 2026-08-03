package com.worker_service.repository;

import com.worker_service.entity.WorkerAddress;
import com.worker_service.enums.AddressType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkerAddressRepository
        extends JpaRepository<WorkerAddress, Long> {

    List<WorkerAddress> findByWorkerId(Long workerId);
}