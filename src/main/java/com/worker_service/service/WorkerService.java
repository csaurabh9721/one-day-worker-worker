package com.worker_service.service;

import com.worker_service.dto.WorkerDTO;
import com.worker_service.dto.WorkerDetailsDto;

import java.util.List;

public interface WorkerService {
    List<WorkerDTO> getWorkers();

    WorkerDTO getWorkerById(Long id);

    WorkerDetailsDto getWorkerDetailById(Long id);

    WorkerDTO addWorker(WorkerDTO dto);

    WorkerDTO updateWorker(Long id, WorkerDTO dto);

    boolean deleteWorkerById(Long id);

}
