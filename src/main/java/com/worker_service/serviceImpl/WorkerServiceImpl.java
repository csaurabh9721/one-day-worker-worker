package com.worker_service.serviceImpl;

import com.worker_service.dto.WorkerDTO;
import com.worker_service.entity.Worker;
import com.worker_service.globleException.WorkerNotFoundException;
import com.worker_service.repository.WorkerRepository;
import com.worker_service.service.WorkerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WorkerServiceImpl implements WorkerService {
    private WorkerRepository repository;
    private ModelMapper modelMapper;

    @Override
    public List<WorkerDTO> getWorkers() {
        List<Worker> workers = repository.findAll();
        return workers.stream().map(this::convertToDto).toList();
    }

    @Override
    public WorkerDTO getWorkerById(Long id) {
       Worker worker = repository.findById(id).orElseThrow(() -> new WorkerNotFoundException("worker", id));
        return convertToDto(worker);
    }

    @Override
    public WorkerDTO addWorker(WorkerDTO dto) {
       Worker workers = convertToEntity(dto);
        Worker savedWorker = repository.save(workers);
        return convertToDto(savedWorker);
    }

    @Override
    public WorkerDTO updateWorker(Long id, WorkerDTO dto) {
        Worker existingWorker = repository.findById(id)
                .orElseThrow(() -> new WorkerNotFoundException("worker", id));

        existingWorker.setName(dto.getName());
        existingWorker.setAge(dto.getAge());
        existingWorker.setMobile(dto.getMobile());
        existingWorker.setPhoto(dto.getPhoto());
        existingWorker.setAddress(existingWorker.getAddress());
        Worker savedWorker = repository.save(existingWorker);
        return convertToDto(savedWorker);
    }

    @Override
    public boolean deleteWorkerById(Long id) {
        Worker worker = repository.findById(id).orElseThrow(() -> new WorkerNotFoundException("worker", id));
        repository.deleteById(id);
        return true;
    }

    public WorkerDTO convertToDto(Worker workerEntity) {
        return modelMapper.map(workerEntity, WorkerDTO.class);
    }

    public Worker convertToEntity(WorkerDTO workerDto) {
        return modelMapper.map(workerDto, Worker.class);
    }

}
