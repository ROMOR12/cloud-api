package com.roberto.cloud_api.usecase;

import com.roberto.cloud_api.dto.response.ImportResponse;
import com.roberto.cloud_api.factory.ParserFactory;
import com.roberto.cloud_api.model.AuditRecord;
import com.roberto.cloud_api.model.BillingRecord;
import com.roberto.cloud_api.parser.DataParser;
import com.roberto.cloud_api.repository.AuditRepository;
import com.roberto.cloud_api.repository.BillingRepository;
import com.roberto.cloud_api.util.DataCleaner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Transactional
@Component
public class ImportDataUseCase {
    private final ParserFactory parserFactory;
    private final AuditRepository auditRepository;
    private final BillingRepository billingRepository;
    private final DataCleaner dataCleaner;

    private static final int BATCH_SIZE = 1000;

    public ImportDataUseCase(ParserFactory parserFactory, AuditRepository auditRepository,
                             BillingRepository billingRepository, DataCleaner dataCleaner) {
        this.parserFactory = parserFactory;
        this.auditRepository = auditRepository;
        this.billingRepository = billingRepository;
        this.dataCleaner = dataCleaner;
    }

    public ImportResponse execute(MultipartFile[] files) {
        int inserted = 0;
        int duplicated = 0;
        int failed = 0;
        
        Set<String> existingBillingHashes = billingRepository.findAllBillingHashes();
        Set<String> existingAuditHashes = auditRepository.findAllAuditHashes();

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            List<BillingRecord> billingRecords = new ArrayList<>();
            List<AuditRecord> auditRecords = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;
                boolean isFirstLine = true;
                DataParser<?> parser = null;

                while ((line = reader.readLine()) != null) {
                    if (dataCleaner.isGarbage(line)) continue;

                    if (isFirstLine) {
                        parser = parserFactory.getParser(line);
                        isFirstLine = false;
                    } else {
                        line = dataCleaner.sanitizeLine(line);
                        if (line.isEmpty()) continue;

                        Object record = parser.parse(line);

                        if (record == null) {
                            failed++;
                            continue;
                        }

                        if (record instanceof BillingRecord billing) {
                            if (billing.getBillingHash() != null && existingBillingHashes.contains(billing.getBillingHash())) {
                                duplicated++;
                                continue;
                            }

                            existingBillingHashes.add(billing.getBillingHash());

                            billingRecords.add(billing);
                            inserted++;

                            if (billingRecords.size() >= BATCH_SIZE) {
                                billingRepository.saveAll(billingRecords);
                                billingRecords.clear();
                            }
                        } else if (record instanceof AuditRecord audit) {
                            // 3. LO MISMO PARA AUDIT
                            if (audit.getEventHash() != null && existingAuditHashes.contains(audit.getEventHash())) {
                                duplicated++;
                                continue;
                            }

                            existingAuditHashes.add(audit.getEventHash());

                            auditRecords.add(audit);
                            inserted++;

                            if (auditRecords.size() >= BATCH_SIZE) {
                                auditRepository.saveAll(auditRecords);
                                auditRecords.clear();
                            }
                        }
                    }
                }

                if (!billingRecords.isEmpty()) billingRepository.saveAll(billingRecords);
                if (!auditRecords.isEmpty()) auditRepository.saveAll(auditRecords);

            } catch (IOException e) {
                failed++;
            }
        }

        ImportResponse response = new ImportResponse();
        response.setMessage("Import process finished successfully.");
        response.setInsertedRecords(inserted);
        response.setDuplicatedRecords(duplicated);
        response.setFailedRecords(failed);

        return response;
    }
}