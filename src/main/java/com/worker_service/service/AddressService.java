package com.worker_service.service;

import com.worker_service.dto.AddressDTO;
import com.worker_service.dto.WorkerDTO;

import java.util.List;

public interface AddressService {
    WorkerDTO saveAddress(Long workerId, AddressDTO addressDTO);
    List<AddressDTO> getAddresses();
}
