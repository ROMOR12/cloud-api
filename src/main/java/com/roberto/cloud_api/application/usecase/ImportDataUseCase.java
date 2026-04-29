package com.roberto.cloud_api.application.usecase;

import com.roberto.cloud_api.application.orchestrator.ImportOrchestrator;
import com.roberto.cloud_api.application.dto.response.ImportResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImportDataUseCase {

    private final ImportOrchestrator orchestrator;

    public ImportDataUseCase(ImportOrchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }

    public ImportResponse execute(MultipartFile[] files) {
        return orchestrator.execute(files);
    }
}