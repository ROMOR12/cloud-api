package com.roberto.cloud_api.dto.response;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AuditResponse {

    Long id;
    LocalDateTime timestamp;
    LocalDate eventDate;
    String tenant;
    String projectId;
    String principalEmail;
    String serviceName;
    String methodName;
    String severity;
    String severityLevel;
    String eventHash;
    String sourceIp;
    String userAgent;
    String identityType;
    String identityName;
    String region;
    String errorCode;
}
