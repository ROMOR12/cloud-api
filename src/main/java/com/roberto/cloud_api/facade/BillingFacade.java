package com.roberto.cloud_api.facade;

import com.roberto.cloud_api.dto.request.BillingFilterRequest;
import com.roberto.cloud_api.dto.response.BillingResponse;
import com.roberto.cloud_api.usecase.billing.DeleteBillingUseCase;
import com.roberto.cloud_api.usecase.billing.GetBillingByFiltersUseCase;
import com.roberto.cloud_api.usecase.billing.GetBillingUseCase;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * This class acts as the middleman between the web controller and our core business logic.
 * Instead of putting all the messy code in the controller, it just calls the right use cases
 * for searching, paginating, and deleting records. It keeps everything clean and organized.
 */
@Component
public class BillingFacade {

    private final GetBillingUseCase getBillingUseCase;
    private final GetBillingByFiltersUseCase getBillingByFiltersUseCase;
    private final DeleteBillingUseCase deleteBillingUseCase;

    public BillingFacade(GetBillingUseCase getBillingUseCase, GetBillingByFiltersUseCase getBillingByFiltersUseCase, DeleteBillingUseCase deleteBillingUseCase) {
        this.getBillingUseCase = getBillingUseCase;
        this.getBillingByFiltersUseCase = getBillingByFiltersUseCase;
        this.deleteBillingUseCase = deleteBillingUseCase;
    }

    public Page<BillingResponse> getPaginatedBilling(int page, int size) {
        return getBillingUseCase.getAllPaginated(page, size);
    }

    public List<BillingResponse> getAllBilling() {
        return getBillingUseCase.getAll();
    }

    public BillingResponse getBillingById(Long id) {
        return getBillingUseCase.getById(id);
    }

    public Page<BillingResponse> searchBilling(BillingFilterRequest filter, int page, int size) {
        return getBillingByFiltersUseCase.execute(filter, page, size);
    }

    public void deleteBilling(Long id) {
        deleteBillingUseCase.execute(id);
    }

    public void deleteBillingByHash(String hash) {
        deleteBillingUseCase.executeByHash(hash);
    }
}
