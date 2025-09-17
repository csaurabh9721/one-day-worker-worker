package com.worker_service.serviceImpl;

import com.worker_service.dto.*;
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

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class WorkerServiceImpl implements WorkerService {
    private final WorkerRepository repository;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;
    private final UserFeignService userFeignService;

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
        /// Services communicate using Rest templates
        String url = "http://RATING-SERVICE/ratingApi/ratings/getRatingsByReceiverId/" + worker.getId();
        ResponseEntity<ApiResponse<List<RatingDTO>>> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {
                        }
                );
        ApiResponse<List<RatingDTO>> apiResponse = response.getBody();
        List<RatingDTO> ratingList;
        if (apiResponse != null && apiResponse.getData() != null) {
            ratingList = apiResponse.getData();
        } else {
            ratingList = Collections.emptyList();
            details.setRatingList(ratingList);
            return details;
        }
        List<RatingDTO> returRatingList = ratingList.stream().peek(rating -> {
            /// Services communicate using Feign Client
            ApiResponse<UsersDTO> userResponse = userFeignService.users(rating.getGiverId().toString());
            if (userResponse != null && userResponse.getData() != null) {
                rating.setUser(userResponse.getData());
            }
        }).toList();

        details.setRatingList(returRatingList);
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
