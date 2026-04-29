package com.roberto.cloud_api.application.usecase.billing;

import com.roberto.cloud_api.application.dto.request.BillingFilterRequest;
import com.roberto.cloud_api.application.dto.response.BillingResponse;
import com.roberto.cloud_api.application.mapper.BillingMapper;
import com.roberto.cloud_api.domain.model.BillingRecord;
import com.roberto.cloud_api.infrastructure.repository.BillingRepository;
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
public class GetBillingByFiltersUseCase {

    private final BillingRepository billingRepository;

    public GetBillingByFiltersUseCase(BillingRepository billingRepository) {
        this.billingRepository = billingRepository;
    }

    public Page<BillingResponse> execute(BillingFilterRequest filter, int page, int size) {
        Specification<BillingRecord> spec = buildSpecification(filter);
        return billingRepository.findAll(spec, PageRequest.of(page, size))
                .map(BillingMapper::toResponse);
    }

    private Specification<BillingRecord> buildSpecification(BillingFilterRequest filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter == null) return criteriaBuilder.conjunction();

            if (filter.getInvoiceId() != null && !filter.getInvoiceId().isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("invoiceId"), filter.getInvoiceId()));
            }
            if (filter.getServiceName() != null && !filter.getServiceName().isBlank()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("serviceName")),
                        "%" + filter.getServiceName().toLowerCase() + "%"));
            }
            if (filter.getRegion() != null && !filter.getRegion().isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("region"), filter.getRegion()));
            }
            if (filter.getCostCategory() != null && !filter.getCostCategory().isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("costCategory"), filter.getCostCategory()));
            }
            if (filter.getStartDate() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("usageDate"), filter.getStartDate()));
            }
            if (filter.getEndDate() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("usageDate"), filter.getEndDate()));
            }
            if (filter.getCloud() != null && !filter.getCloud().isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("cloud"), filter.getCloud()));
            }

            if (filter.getProjectId() != null && !filter.getProjectId().isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("projectId"), filter.getProjectId()));
            }
            if (filter.getBillingPeriod() != null && !filter.getBillingPeriod().isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("billingPeriod"), filter.getBillingPeriod()));
            }
            if (filter.getMinCost() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("cost"), filter.getMinCost()));
            }
            if (filter.getMaxCost() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("cost"), filter.getMaxCost()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
