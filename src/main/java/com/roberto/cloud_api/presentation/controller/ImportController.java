package com.roberto.cloud_api.presentation.controller;

import com.roberto.cloud_api.application.dto.response.ImportResponse;
import com.roberto.cloud_api.application.facade.ImportFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * This controller is in charge of uploading files.
 * It takes the big CSV files you upload and sends them to the ImportFacade
 * so it can process all that data and save it to the database without freezing the app.
 */
@RequestMapping("/api")
@RestController
public class ImportController {

    private final ImportFacade importFacade;

    public ImportController(ImportFacade importFacade) {
        this.importFacade = importFacade;
    }

    @PostMapping("/import")
    public ResponseEntity<ImportResponse> importData(@RequestParam("files") MultipartFile[] files) {
        return ResponseEntity.ok(importFacade.importData(files));
    }
}