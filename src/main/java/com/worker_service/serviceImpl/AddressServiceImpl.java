package com.worker_service.serviceImpl;

import com.worker_service.dto.AddressDTO;
import com.worker_service.dto.WorkerDTO;
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
    @Transactional
    public WorkerDTO saveAddress(Long workerId, AddressDTO dto) {
        Worker workerEntity = workerRepository.findById(workerId)
                .orElseThrow(() -> new WorkerNotFoundException("worker", workerId));
        if(workerEntity.getAddress() == null){
            Address addressEntity = convertToEntity(dto);
            addressEntity.setWorker(workerEntity);
            Address savedAddress = repository.save(addressEntity);
            workerEntity.setAddress(savedAddress);
            workerRepository.save(workerEntity);
        }else {
            Address addressEntity = workerEntity.getAddress();
            addressEntity.setStreetName(dto.getStreetName());
            addressEntity.setSubLocality(dto.getSubLocality());
            addressEntity.setLocality(dto.getLocality());
            addressEntity.setCity(dto.getCity());
            addressEntity.setState(dto.getState());
            addressEntity.setCountry(dto.getCountry());
            addressEntity.setPinCode(dto.getPinCode());
            Address savedAddress = repository.save(addressEntity);
            workerEntity.setAddress(savedAddress);
            workerRepository.save(workerEntity);
        }

        return modelMapper.map(workerEntity, WorkerDTO.class);
    }



    public AddressDTO convertToDto(Address workerEntity) {
        return modelMapper.map(workerEntity, AddressDTO.class);
    }

    public Address convertToEntity(AddressDTO workerDto) {
        return modelMapper.map(workerDto, Address.class);
    }

}
