package com.roberto.cloud_api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class AuditRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDateTime timestamp;
    LocalDate eventDate;
    String tenant;
    String projectId;
    @Column(columnDefinition = "TEXT")
    String principalEmail;
    String serviceName;
    String methodName;
    String severity;
    String severityLevel;
    String eventHash;
    String sourceIp;
    @Column(columnDefinition = "TEXT")
    String userAgent;
    String identityType;
    String identityName;
    String region;
    String errorCode;
}
