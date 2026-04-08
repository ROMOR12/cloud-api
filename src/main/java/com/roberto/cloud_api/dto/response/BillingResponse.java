package com.roberto.cloud_api.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BillingResponse {

    Long id;
    String tenant;
    String cloud;
    LocalDateTime exportTime;
    String invoiceId;
    String billingPeriod;
    LocalDate usageDate;
    LocalDateTime usageStartTime;
    LocalDateTime usageEndTime;
    String billingAccountId;
    String consumerAccountId;
    String projectId;
    String resourceId;
    String region;
    String serviceName;
    String skuName;
    String operation;
    BigDecimal cost;
    String currency;
    String costCategory;
    String platform;
    String service;
    String tags;
    String sourcePayload;
    String billingHash;
}
