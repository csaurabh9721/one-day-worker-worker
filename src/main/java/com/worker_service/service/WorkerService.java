package com.worker_service.service;

import com.worker_service.dto.SaveWorkerDto;
import com.worker_service.dto.WorkerReturnDTO;
import com.worker_service.dto.WorkerDetailsDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface WorkerService {
    List<WorkerReturnDTO> getWorkers();

    WorkerReturnDTO getWorkerById(Long id);

    WorkerDetailsDto getWorkerDetailById(Long id);

    WorkerReturnDTO addWorker(SaveWorkerDto dto);

    WorkerReturnDTO updateWorker(Long id, WorkerReturnDTO dto);

    boolean deleteWorkerById(Long id);
    String saveUserProfilePic(Long id, MultipartFile file) ;

}
