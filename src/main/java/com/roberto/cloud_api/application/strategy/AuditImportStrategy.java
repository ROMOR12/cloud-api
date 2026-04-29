package com.roberto.cloud_api.application.strategy;

import com.roberto.cloud_api.domain.model.AuditRecord;
import com.roberto.cloud_api.infrastructure.persistence.BatchSaver;
import com.roberto.cloud_api.infrastructure.repository.AuditRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequestScope
public class AuditImportStrategy implements ImportStrategy<AuditRecord> {

    private final AuditRepository auditRepository;
    private final BatchSaver batchSaver;

    private final Set<String> seenInThisImport = new HashSet<>();
    private final List<AuditRecord> buffer = new ArrayList<>();
    private int inserted = 0;
    private int duplicated = 0;

    private static final int BATCH_SIZE = 1000;

    public AuditImportStrategy(AuditRepository auditRepository, BatchSaver batchSaver) {
        this.auditRepository = auditRepository;
        this.batchSaver = batchSaver;
    }

    @Override
    public boolean supports(Object record) {
        return record instanceof AuditRecord;
    }

    @Override
    public boolean isDuplicate(AuditRecord record) {
        String hash = record.getEventHash();
        if (hash == null) return false;
        return seenInThisImport.contains(hash) || auditRepository.existsByEventHash(hash);
    }

    @Override
    public void accumulate(AuditRecord record) {
        seenInThisImport.add(record.getEventHash());
        buffer.add(record);
        inserted++;
        if (buffer.size() >= BATCH_SIZE) flush();
    }

    @Override
    public void flush() {
        if (!buffer.isEmpty()) {
            batchSaver.saveAuditBatch(new ArrayList<>(buffer));
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