package com.roberto.cloud_api.usecase.audit;

import com.roberto.cloud_api.repository.AuditRepository;
import org.springframework.stereotype.Component;

/**
 * This tool handles the safe removal of an audit record.
 * It checks the database first to make sure the record actually exists before deleting it,
 * which prevents the app from throwing ugly errors.
 */
@Component
public class DeleteAuditUseCase {

    private final AuditRepository auditRepository;

    public DeleteAuditUseCase(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    public void execute(Long id) {
        if (!auditRepository.existsById(id)) {
            throw new RuntimeException("Error: Audit record with ID " + id + " does not exist.");
        }
        auditRepository.deleteById(id);
    }

    public void executeByHash(String hash) {
        if (!auditRepository.existsByEventHash(hash)) {
            throw new RuntimeException("Error: Audit record with hash " + hash + " does not exist.");
        }
        auditRepository.deleteByEventHash(hash);
    }
}
