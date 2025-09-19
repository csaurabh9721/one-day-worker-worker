package com.worker_service.serviceImpl;

import com.worker_service.dto.*;
import com.worker_service.entity.Worker;
import com.worker_service.globleException.WorkerNotFoundException;
import com.worker_service.repository.WorkerRepository;
import com.worker_service.service.UserFeignService;
import com.worker_service.service.WorkerService;
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
    public WorkerDetailsDto getWorkerDetailById(Long id) {
        Worker worker = repository.findById(id).orElseThrow(() -> new WorkerNotFoundException("worker", id));
        WorkerDetailsDto details = modelMapper.map(worker, WorkerDetailsDto.class);
        List<RatingDTO> returRatingList = getRatings(worker.getId());
        details.setRatingList(returRatingList);
        return details;
    }

    /// Services communicate using Rest templates
    private List<RatingDTO> getRatings(Long workerId) {
        try {
            String url = "http://RATING-SERVICE/ratingService/ratings/getRatingsByReceiverId/" + workerId;
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
                return Collections.emptyList();
            }
            return ratingList.stream().peek(rating -> {
                UsersDTO user = getUSer(rating.getGiverId());
                rating.setUser(user);
            }).toList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    /// Services communicate using Feign Client
    private UsersDTO getUSer(Long userId) {
        try {
            ApiResponse<UsersDTO> response = userFeignService.users(userId.toString());
            if (response != null && response.getData() != null) {
                return response.getData();
            } else {
                return null;
            }
        } catch (Exception e) {
            log.info(e.toString());
            return null;
        }
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
