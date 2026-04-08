package com.roberto.cloud_api.usecase.audit;

import com.roberto.cloud_api.dto.response.AuditResponse;
import com.roberto.cloud_api.mapper.AuditMapper;
import com.roberto.cloud_api.repository.AuditRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * This class handles the basic fetching of data.
 * It can grab a single record by its ID or return a paginated list of records
 * so we do not overload the screen with thousands of rows at once.
 */
@Component
public class GetAuditUseCase {

    private final AuditRepository auditRepository;

    public GetAuditUseCase(AuditRepository auditRepository){
        this.auditRepository = auditRepository;
    }

    public List<AuditResponse> getAll(){
        return auditRepository.findAll()
                .stream()
                .map(AuditMapper::toResponse)
                .toList();
    }

    public Page<AuditResponse> getAllPaginated(int page, int size) {
        return auditRepository.findAll(PageRequest.of(page, size))
                .map(AuditMapper::toResponse);
    }

    public AuditResponse getById(Long id){
        return auditRepository.findById(id)
                .map(AuditMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Audit not found: " + id));
    }
}
