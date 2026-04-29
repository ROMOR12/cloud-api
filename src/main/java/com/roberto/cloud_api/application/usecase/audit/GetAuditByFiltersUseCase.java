package com.roberto.cloud_api.application.usecase.audit;

import com.roberto.cloud_api.application.dto.request.AuditFilterRequest;
import com.roberto.cloud_api.application.dto.response.AuditResponse;
import com.roberto.cloud_api.application.mapper.AuditMapper;
import com.roberto.cloud_api.domain.model.AuditRecord;
import com.roberto.cloud_api.infrastructure.repository.AuditRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * This is where the advanced search magic happens.
 * It takes all the optional filters the user picked, like dates or categories,
 * and builds a custom database query on the fly so we only get the exact records they are looking for.
 */
@Component
public class GetAuditByFiltersUseCase {

    private final AuditRepository auditRepository;

    public GetAuditByFiltersUseCase(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    public Page<AuditResponse> execute(AuditFilterRequest filter, int page, int size) {
        Specification<AuditRecord> spec = buildSpecification(filter);
        return auditRepository.findAll(spec, PageRequest.of(page, size))
                .map(AuditMapper::toResponse);
    }

    private Specification<AuditRecord> buildSpecification(AuditFilterRequest filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter == null) {
                return criteriaBuilder.conjunction();
            }

            if (filter.getPrincipalEmail() != null && !filter.getPrincipalEmail().isBlank()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("principalEmail")), "%" + filter.getPrincipalEmail().toLowerCase() + "%"));
            }
            if (filter.getIdentityName() != null && !filter.getIdentityName().isBlank()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("identityName")), "%" + filter.getIdentityName().toLowerCase() + "%"));
            }

            if (filter.getErrorCode() != null && !filter.getErrorCode().isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("errorCode"), filter.getErrorCode()));
            }
            if (filter.getSourceIp() != null && !filter.getSourceIp().isBlank()) {
                predicates.add(criteriaBuilder.like(root.get("sourceIp"), "%" + filter.getSourceIp() + "%"));
            }

            if (filter.getMethodName() != null && !filter.getMethodName().isBlank()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("methodName")), "%" + filter.getMethodName().toLowerCase() + "%"));
            }

            if (filter.getSeverity() != null && !filter.getSeverity().isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("severity"), filter.getSeverity()));
            }
            if (filter.getSeverityLevel() != null && !filter.getSeverityLevel().isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("severityLevel"), filter.getSeverityLevel()));
            }

            if (filter.getStartDate() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("eventDate"), filter.getStartDate()));
            }
            if (filter.getEndDate() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("eventDate"), filter.getEndDate()));
            }

            if (filter.getStartTimestamp() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("timestamp"), filter.getStartTimestamp()));
            }
            if (filter.getEndTimestamp() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("timestamp"), filter.getEndTimestamp()));
            }

            if (filter.getProjectId() != null && !filter.getProjectId().isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("projectId"), filter.getProjectId()));
            }
            if (filter.getServiceName() != null && !filter.getServiceName().isBlank()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("serviceName")), "%" + filter.getServiceName().toLowerCase() + "%"));
            }
            if (filter.getRegion() != null && !filter.getRegion().isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("region"), filter.getRegion()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
