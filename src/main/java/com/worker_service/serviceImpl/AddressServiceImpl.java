package com.worker_service.serviceImpl;

import com.worker_service.dto.AddressDTO;
import com.worker_service.entity.Address;
import com.worker_service.entity.Worker;
import com.worker_service.globleException.WorkerNotFoundException;
import com.worker_service.repository.AddressRepository;
import com.worker_service.repository.WorkerRepository;
import com.worker_service.service.AddressService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
    private AddressRepository repository;
    private ModelMapper modelMapper;
    private WorkerRepository workerRepository;

    @Override
    public List<AddressDTO> getAddresses() {
        List<Address> workers = repository.findAll();
        return workers.stream().map(this::convertToDto).toList();
    }

    @Override
    public AddressDTO getAddressesByUserId(Long workerId) {
        Address address = repository.findByWorkerId(workerId);
        return convertToDto(address);
    }

    @Override
    @Transactional
    public AddressDTO saveAddress(Long workerId, AddressDTO dto) {
        Worker workerEntity = workerRepository.findById(workerId)
                .orElseThrow(() -> new WorkerNotFoundException("worker", workerId));
        Address addressEntity = workerEntity.getAddress();
        if (addressEntity == null) {
            addressEntity = convertToEntity(dto);
            addressEntity.setWorker(workerEntity);
        } else {
            modelMapper.map(dto, addressEntity);
        }
        Address savedAddress = repository.save(addressEntity);
        return modelMapper.map(savedAddress, AddressDTO.class);
    }


    public AddressDTO convertToDto(Address workerEntity) {
        return modelMapper.map(workerEntity, AddressDTO.class);
    }

    public Address convertToEntity(AddressDTO workerDto) {
        return modelMapper.map(workerDto, Address.class);
    }

}
