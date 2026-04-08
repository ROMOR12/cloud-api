package com.roberto.cloud_api.facade;

import com.roberto.cloud_api.dto.request.AuditFilterRequest;
import com.roberto.cloud_api.dto.response.AuditResponse;
import com.roberto.cloud_api.usecase.audit.DeleteAuditUseCase;
import com.roberto.cloud_api.usecase.audit.GetAuditByFiltersUseCase;
import com.roberto.cloud_api.usecase.audit.GetAuditUseCase;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * This class acts as the middleman between the web controller and our core business logic.
 * Instead of putting all the messy code in the controller, it just calls the right use cases
 * for searching, paginating, and deleting records. It keeps everything clean and organized.
 */
@Component
public class AuditFacade {

    private final GetAuditUseCase getAuditUseCase;
    private final GetAuditByFiltersUseCase getAuditByFiltersUseCase;
    private final DeleteAuditUseCase deleteAuditUseCase;

    public AuditFacade(GetAuditUseCase getAuditUseCase, GetAuditByFiltersUseCase getAuditByFiltersUseCase, DeleteAuditUseCase deleteAuditUseCase) {
        this.getAuditUseCase = getAuditUseCase;
        this.getAuditByFiltersUseCase = getAuditByFiltersUseCase;
        this.deleteAuditUseCase = deleteAuditUseCase;
    }

    public Page<AuditResponse> getPaginatedAudits(int page, int size) {
        return getAuditUseCase.getAllPaginated(page, size);
    }

    public Page<AuditResponse> searchAudit(AuditFilterRequest filter, int page, int size) {
        return getAuditByFiltersUseCase.execute(filter, page, size);
    }

    public List<AuditResponse> getAllAudits() {
        return getAuditUseCase.getAll();
    }

    public AuditResponse getAuditById(Long id) {
        return getAuditUseCase.getById(id);
    }

    public void deleteAudit(Long id) {
        deleteAuditUseCase.execute(id);
    }

    public void deleteAuditByHash(String hash) {
        deleteAuditUseCase.executeByHash(hash);
    }
}
