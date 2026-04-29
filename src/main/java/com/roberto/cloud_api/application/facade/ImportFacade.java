package com.roberto.cloud_api.application.facade;

import com.roberto.cloud_api.application.dto.response.ImportResponse;
import com.roberto.cloud_api.application.usecase.ImportDataUseCase;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * This is the manager for the file upload process.
 * It grabs the uploaded file from the controller and passes it straight to the big import use case
 * to do the heavy lifting of reading and saving the data.
 */
@Component
public class ImportFacade {

    private final ImportDataUseCase importDataUseCase;

    public ImportFacade(ImportDataUseCase importDataUseCase) {
        this.importDataUseCase = importDataUseCase;
    }

    public ImportResponse importData(MultipartFile[] files) {
        return importDataUseCase.execute(files);
    }
}