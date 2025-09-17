package com.worker_service.serviceImpl;

import com.worker_service.dto.ApiResponse;
import com.worker_service.dto.RatingDTO;
import com.worker_service.dto.WorkerDTO;
import com.worker_service.dto.WorkerDetailsDto;
import com.worker_service.entity.Worker;
import com.worker_service.globleException.WorkerNotFoundException;
import com.worker_service.repository.WorkerRepository;
import com.worker_service.service.WorkerService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class WorkerServiceImpl implements WorkerService {
    private final WorkerRepository repository;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;

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
    @Transactional
    public WorkerDetailsDto getWorkerDetailById(Long id) {
        Worker worker = repository.findById(id).orElseThrow(() -> new WorkerNotFoundException("worker", id));
        WorkerDetailsDto details = modelMapper.map(worker, WorkerDetailsDto.class);
        log.info("➡️ Calling external API-URL: {}", "http://localhost:8083/ratingApi/ratings/getRatingsByReceiverId/"+worker.getId());

        String url = "http://localhost:8083/ratingApi/ratings/getRatingsByReceiverId/"+worker.getId();

        ResponseEntity<ApiResponse<List<RatingDTO>>> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<ApiResponse<List<RatingDTO>>>() {}
                );
        ApiResponse<List<RatingDTO>> apiResponse = response.getBody();
        if (apiResponse != null && apiResponse.getData() != null) {
            details.setRatingList(apiResponse.getData());
        } else {
            details.setRatingList(Collections.emptyList());
        }
        return details;
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
        // existingWorker.setAddress(existingWorker.getAddress());
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
