package com.roberto.cloud_api.application.orchestrator;

import com.roberto.cloud_api.application.processor.CsvLineProcessor;
import com.roberto.cloud_api.application.processor.ImportResult;
import com.roberto.cloud_api.application.strategy.ImportStrategy;
import com.roberto.cloud_api.application.dto.response.ImportResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Component
@RequestScope
public class ImportOrchestrator {

    private final CsvLineProcessor lineProcessor;
    private final List<ImportStrategy<?>> strategies;

    public ImportOrchestrator(CsvLineProcessor lineProcessor, List<ImportStrategy<?>> strategies) {
        this.lineProcessor = lineProcessor;
        this.strategies = strategies;
    }

    public ImportResponse execute(MultipartFile[] files) {
        ImportResult total = ImportResult.empty();

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                total = total.add(lineProcessor.process(reader, strategies));
            } catch (IOException e) {
                total = total.add(new ImportResult(0, 0, 1));
            }
        }
        return toResponse(total);
    }

    private ImportResponse toResponse(ImportResult result) {
        ImportResponse response = new ImportResponse();
        response.setMessage("Import process finished successfully.");
        response.setInsertedRecords(result.inserted());
        response.setDuplicatedRecords(result.duplicated());
        response.setFailedRecords(result.failed());
        return response;
    }
}