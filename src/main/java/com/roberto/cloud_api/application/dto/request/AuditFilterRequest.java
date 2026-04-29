package com.roberto.cloud_api.application.dto.request;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AuditFilterRequest {
    private String principalEmail;
    private String identityName;
    private String errorCode;
    private String sourceIp;
    private String methodName;
    private String severity;
    private String severityLevel;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime startTimestamp;
    private LocalDateTime endTimestamp;
    private String projectId;
    private String serviceName;
    private String region;
}
