package com.roberto.cloud_api.application.dto.request;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BillingFilterRequest {
    private String invoiceId;
    private String serviceName;
    private String region;
    private String costCategory;
    private LocalDate startDate;
    private LocalDate endDate;
    private String billingPeriod;
    private String cloud;
    private String projectId;
    private BigDecimal minCost;
    private BigDecimal maxCost;
}
