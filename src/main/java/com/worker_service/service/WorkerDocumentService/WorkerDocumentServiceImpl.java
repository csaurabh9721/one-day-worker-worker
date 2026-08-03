package com.worker_service.service.WorkerDocumentService;
import com.worker_service.dto.document.WorkerDocumentCreateRequest;
import com.worker_service.dto.document.WorkerDocumentResponse;
import com.worker_service.dto.document.WorkerDocumentUpdateRequest;
import com.worker_service.entity.Worker;
import com.worker_service.entity.WorkerDocument;
import com.worker_service.enums.DocumentStatus;
import com.worker_service.globleException.DuplicateResourceException;
import com.worker_service.globleException.ResourceNotFoundException;
import com.worker_service.mapper.WorkerDocumentMapper;
import com.worker_service.repository.WorkerDocumentRepository;
import com.worker_service.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkerDocumentServiceImpl
        implements WorkerDocumentService {

    private final WorkerDocumentRepository repository;
    private final WorkerRepository workerRepository;
    private final WorkerDocumentMapper mapper;

    @Override
    @Transactional
    public WorkerDocumentResponse uploadDocument(
            Long workerId,
            WorkerDocumentCreateRequest request) {

        Worker worker = findWorker(workerId);

        if (repository.existsByWorkerIdAndDocumentType(
                workerId,
                request.documentType())) {

            throw new DuplicateResourceException(
                    "Document of this type already exists"
            );
        }

        WorkerDocument document =
                mapper.toEntity(request);

        document.setWorker(worker);
        document.setStatus(DocumentStatus.PENDING);

        return mapper.toResponse(
                repository.save(document)
        );
    }

    @Override
    public List<WorkerDocumentResponse> getWorkerDocuments(
            Long workerId) {

        return repository.findByWorkerId(workerId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public WorkerDocumentResponse updateDocument(
            Long workerId,
            Long documentId,
            WorkerDocumentUpdateRequest request) {

        WorkerDocument document =
                findDocument(
                        workerId,
                        documentId
                );

        mapper.updateEntity(request, document);

        /*
         * Once document details are changed,
         * previous verification should no longer be trusted.
         */
        document.setStatus(DocumentStatus.PENDING);

        return mapper.toResponse(
                repository.save(document)
        );
    }

    @Override
    @Transactional
    public void deleteDocument(
            Long workerId,
            Long documentId) {

        WorkerDocument document =
                findDocument(
                        workerId,
                        documentId
                );

        repository.delete(document);
    }

    @Override
    @Transactional
    public WorkerDocumentResponse updateDocumentStatus(
            Long documentId,
            DocumentStatus status,
            String reviewComment,
            Long reviewerId) {

        WorkerDocument document =
                repository.findById(documentId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Document not found: "
                                                + documentId
                                ));

        document.setStatus(status);

        /*
         * These fields must exist in your entity if you want
         * to persist reviewer/review information.
         */
        // document.setReviewComment(reviewComment);
        // document.setVerifiedBy(reviewerId);

        return mapper.toResponse(
                repository.save(document)
        );
    }

    private Worker findWorker(Long workerId) {

        return workerRepository.findById(workerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Worker not found: " + workerId
                        ));
    }

    private WorkerDocument findDocument(
            Long workerId,
            Long documentId) {

        WorkerDocument document =
                repository.findById(documentId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Document not found"
                                ));

        if (!document.getWorker()
                .getId()
                .equals(workerId)) {

            throw new ResourceNotFoundException(
                    "Document not found"
            );
        }

        return document;
    }
}