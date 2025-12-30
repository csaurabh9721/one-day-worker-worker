package com.worker_service.serviceImpl;

import com.worker_service.dto.*;
import com.worker_service.entity.Worker;
import com.worker_service.globleException.WorkerNotFoundException;
import com.worker_service.repository.WorkerRepository;
import com.worker_service.service.RatingFeign;
import com.worker_service.service.UserFeignService;
import com.worker_service.service.WorkerService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private final RatingFeign ratingFeign;

    // -------------------- BASIC CRUD --------------------

    @Override
    public List<WorkerDTO> getWorkers() {
        return repository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public WorkerDTO getWorkerById(Long id) {
        Worker worker = repository.findById(id)
                .orElseThrow(() -> new WorkerNotFoundException("worker", id));
        return convertToDto(worker);
    }

    @Override
    public WorkerDetailsDto getWorkerDetailById(Long id) {

        Worker worker = repository.findById(id)
                .orElseThrow(() -> new WorkerNotFoundException("worker", id));
        WorkerDetailsDto details = modelMapper.map(worker, WorkerDetailsDto.class);
        List<RatingDTO> ratings = getRatings(worker.getId());

        if (ratings != null && !ratings.isEmpty()) {
            ratings.forEach(rt -> {
                UsersDTO user = getUser(rt.getGiverId());
                rt.setUser(user);
            });
        }
        details.setRatingList(ratings);
        return details;
    }

    // -------------------- RATING SERVICE (RestTemplate) --------------------
/// Services communicate using ""Rest templates""
/// this is not recommended
/// use for ony legacy project
/// It is use without adding any dependency, Much boilerplate code
    /**
     * Remote call → MUST be protected by Circuit Breaker
     * NO try-catch here ❌
     */
    @CircuitBreaker(
            name = "Rating-Service",
            fallbackMethod = "ratingServiceFallback"
    )
    public List<RatingDTO> getRatings(Long workerId) {

//        String url =
//                "http://RATING-SERVICE/ratingService/ratings/getRatingsByReceiverId/" + workerId;
//
//        ResponseEntity<ApiResponse<List<RatingDTO>>> response =
//                restTemplate.exchange(
//                        url,
//                        HttpMethod.GET,
//                        null,
//                        new ParameterizedTypeReference<>() {
//                        }
//                );
        ApiResponse<List<RatingDTO>> response =
                ratingFeign.getRatingsByReceiverId(workerId.toString());
        if (response == null || response.getData() == null) {
            return Collections.emptyList();
        }
        return response.getData();
    }

    /**
     * Fallback must be FAST, SAFE, NO DB, NO REMOTE
     */
    public List<RatingDTO> ratingServiceFallback(Long workerId, Throwable ex) {
        log.error("Rating service DOWN for workerId {}", workerId, ex);
        return Collections.emptyList();
    }

    // -------------------- USER SERVICE (Feign Client) --------------------

    /// Services communicate using ""Feign Client""
    ///  /// Higher Recommended
    /// /// Commonly use in modern Spring boot
    ///  /// Less boilerplate code, required dependency to use it
    @CircuitBreaker(
            name = "User-Service",
            fallbackMethod = "userServiceFallback"
    )
    public UsersDTO getUser(Long userId) {

        ApiResponse<UsersDTO> response =
                userFeignService.users(userId.toString());

        return response != null ? response.getData() : null;
    }

    public UsersDTO userServiceFallback(Long userId, Throwable ex) {
        log.error("User service DOWN for userId {}", userId, ex);
        return null;
    }

    // -------------------- WRITE OPERATIONS --------------------

    @Override
    public WorkerDTO addWorker(WorkerDTO dto) {
        Worker worker = convertToEntity(dto);
        Worker saved = repository.save(worker);
        return convertToDto(saved);
    }

    @Override
    public WorkerDTO updateWorker(Long id, WorkerDTO dto) {

        Worker worker = repository.findById(id)
                .orElseThrow(() -> new WorkerNotFoundException("worker", id));

        worker.setName(dto.getName());
        worker.setAge(dto.getAge());
        worker.setMobile(dto.getMobile());
        worker.setPhoto(dto.getPhoto());

        Worker updated = repository.save(worker);
        return convertToDto(updated);
    }

    @Override
    public boolean deleteWorkerById(Long id) {
        Worker worker = repository.findById(id)
                .orElseThrow(() -> new WorkerNotFoundException("worker", id));
        repository.delete(worker);
        return true;
    }

    // -------------------- FILE UPLOAD --------------------

    public String saveUserProfilePic(Long id, MultipartFile file) {

        Worker worker = repository.findById(id)
                .orElseThrow(() -> new WorkerNotFoundException("worker", id));

        final String uploadDir = "uploads/";
        File dir = new File(uploadDir);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = id + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir + fileName);

        try {
            Files.write(path, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image", e);
        }

        String fileUrl = "/uploads/" + fileName;
        worker.setPhoto(fileUrl);
        repository.save(worker);

        return fileUrl;
    }

    // -------------------- MAPPERS --------------------

    private WorkerDTO convertToDto(Worker entity) {
        return modelMapper.map(entity, WorkerDTO.class);
    }

    private Worker convertToEntity(WorkerDTO dto) {
        return modelMapper.map(dto, Worker.class);
    }
}
