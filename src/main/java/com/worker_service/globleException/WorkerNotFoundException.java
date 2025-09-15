package com.worker_service.globleException;

public class WorkerNotFoundException extends RuntimeException {
    public WorkerNotFoundException(String header, Long id) {
        super(header + " not found with ID " + id);
    }

}
