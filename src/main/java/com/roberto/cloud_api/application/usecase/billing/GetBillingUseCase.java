package com.roberto.cloud_api.application.usecase.billing;

import com.roberto.cloud_api.application.dto.response.BillingResponse;
import com.roberto.cloud_api.application.mapper.BillingMapper;
import com.roberto.cloud_api.infrastructure.repository.BillingRepository;
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
public class GetBillingUseCase {

    private final BillingRepository billingRepository;

    public GetBillingUseCase(BillingRepository billingRepository){
        this.billingRepository = billingRepository;
    }

    public List<BillingResponse> getAll(){
        return billingRepository.findAll()
                .stream()
                .map(BillingMapper::toResponse)
                .toList();
    }

    public Page<BillingResponse> getAllPaginated(int page, int size) {
        return billingRepository.findAll(PageRequest.of(page, size))
                .map(BillingMapper::toResponse);
    }

    public BillingResponse getById(Long id){
        return billingRepository.findById(id)
                .map(BillingMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Billing not found: " + id));
    }
}
