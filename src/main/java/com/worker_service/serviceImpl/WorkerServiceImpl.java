package com.worker_service.serviceImpl;

import com.worker_service.dto.*;
import com.worker_service.entity.Worker;
import com.worker_service.globleException.WorkerNotFoundException;
import com.worker_service.repository.WorkerRepository;
import com.worker_service.service.WorkerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
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
    private final RatingClientService ratingClientService;
    private final UserClientService userClientService;

    // -------------------- BASIC CRUD --------------------

    @Override
    public List<WorkerReturnDTO> getWorkers() {
        return repository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public WorkerReturnDTO getWorkerById(Long id) {
        Worker worker = repository.findById(id)
                .orElseThrow(() -> new WorkerNotFoundException("worker", id));
        return convertToDto(worker);
    }

    @Override
    public WorkerDetailsDto getWorkerDetailById(Long id) {

        Worker worker = repository.findById(id)
                .orElseThrow(() -> new WorkerNotFoundException("worker", id));
        WorkerDetailsDto details = modelMapper.map(worker, WorkerDetailsDto.class);
        List<RatingDTO> ratings;
        try {
            ratings = ratingClientService.getRatings(worker.getId());

        } catch (Exception ex) {
            ratings = Collections.emptyList();
        }

        if (ratings != null && !ratings.isEmpty()) {
            ratings.forEach(rt -> {
                try {
                    UsersDTO user = userClientService.getUser(rt.getGiverId());
                    rt.setUser(user);
                } catch (Exception ex) {
                    log.warn("User data skipped for giverId {}", rt.getGiverId());
                    rt.setUser(null);
                }
            });
        }
        details.setRatingList(ratings);
        return details;
    }



    // -------------------- WRITE OPERATIONS --------------------

    @Override
    public WorkerReturnDTO addWorker(SaveWorkerDto dto) {
        Worker worker = convertToEntity(dto);
        Worker saved = repository.save(worker);
        return convertToDto(saved);
    }

    @Override
    public WorkerReturnDTO updateWorker(Long id, WorkerReturnDTO dto) {

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

    private WorkerReturnDTO convertToDto(Worker entity) {
        return modelMapper.map(entity, WorkerReturnDTO.class);
    }

    private Worker convertToEntity(SaveWorkerDto dto) {
        return modelMapper.map(dto, Worker.class);
    }
}
