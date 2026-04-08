package com.roberto.cloud_api.dto.response;

import lombok.Data;

@Data
public class ImportResponse {
    private String message;
    private int insertedRecords;
    private int duplicatedRecords;
    private int failedRecords;
}