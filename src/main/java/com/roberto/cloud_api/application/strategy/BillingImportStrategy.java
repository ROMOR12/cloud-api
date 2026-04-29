package com.roberto.cloud_api.application.strategy;

import com.roberto.cloud_api.domain.model.BillingRecord;
import com.roberto.cloud_api.domain.port.BillingRepositoryPort;
import com.roberto.cloud_api.infrastructure.persistence.BatchSaver;
import com.roberto.cloud_api.infrastructure.repository.BillingRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequestScope
public class BillingImportStrategy implements ImportStrategy<BillingRecord> {

    private final BillingRepository billingRepository;
    private final BatchSaver batchSaver;

    private final Set<String> seenInThisImport = new HashSet<>();
    private final List<BillingRecord> buffer = new ArrayList<>();
    private int inserted = 0;
    private int duplicated = 0;

    private static final int BATCH_SIZE = 1000;

    public BillingImportStrategy(BillingRepository billingRepository, BatchSaver batchSaver) {
        this.billingRepository = billingRepository;
        this.batchSaver = batchSaver;
    }

    @Override
    public boolean supports(Object record) {
        return record instanceof BillingRecord;
    }

    @Override
    public boolean isDuplicate(BillingRecord record) {
        String hash = record.getBillingHash();
        if (hash == null) return false;
        return seenInThisImport.contains(hash) || billingRepository.existsByBillingHash(hash);
    }

    @Override
    public void accumulate(BillingRecord record) {
        seenInThisImport.add(record.getBillingHash());
        buffer.add(record);
        inserted++;
        if (buffer.size() >= BATCH_SIZE) flush();
    }

    @Override
    public void flush() {
        if (!buffer.isEmpty()) {
            batchSaver.saveBillingBatch(new ArrayList<>(buffer));
            buffer.clear();
        }
    }

    @Override
    public void incrementDuplicated() { duplicated++; }

    @Override
    public int getInserted() { return inserted; }

    @Override
    public int getDuplicated() { return duplicated; }
}