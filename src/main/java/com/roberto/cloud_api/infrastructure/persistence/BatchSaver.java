package com.roberto.cloud_api.infrastructure.persistence;

import com.roberto.cloud_api.domain.model.AuditRecord;
import com.roberto.cloud_api.domain.model.BillingRecord;
import com.roberto.cloud_api.infrastructure.repository.AuditRepository;
import com.roberto.cloud_api.infrastructure.repository.BillingRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
public class BatchSaver {

    private final BillingRepository billingRepository;
    private final AuditRepository auditRepository;

    public BatchSaver(BillingRepository billingRepository, AuditRepository auditRepository) {
        this.billingRepository = billingRepository;
        this.auditRepository = auditRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveBillingBatch(List<BillingRecord> records) {
        billingRepository.saveAll(records);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveAuditBatch(List<AuditRecord> records) {
        auditRepository.saveAll(records);
    }
}