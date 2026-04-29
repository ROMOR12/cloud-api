package com.roberto.cloud_api.application.mapper;

import com.roberto.cloud_api.application.dto.response.BillingResponse;
import com.roberto.cloud_api.domain.model.BillingRecord;

/**
 * Converter class.
 * It takes the heavy database object with all its internal details and copies just the data
 * we want to share into a clean response object ready to be sent to the user.
 */
public class BillingMapper {
    public static BillingResponse toResponse(BillingRecord record) {
        BillingResponse response = new BillingResponse();
        response.setId(record.getId());
        response.setTenant(record.getTenant());
        response.setCloud(record.getCloud());
        response.setExportTime(record.getExportTime());
        response.setInvoiceId(record.getInvoiceId());
        response.setBillingPeriod(record.getBillingPeriod());
        response.setUsageDate(record.getUsageDate());
        response.setUsageStartTime(record.getUsageStartTime());
        response.setUsageEndTime(record.getUsageEndTime());
        response.setBillingAccountId(record.getBillingAccountId());
        response.setConsumerAccountId(record.getConsumerAccountId());
        response.setProjectId(record.getProjectId());
        response.setResourceId(record.getResourceId());
        response.setRegion(record.getRegion());
        response.setServiceName(record.getServiceName());
        response.setSkuName(record.getSkuName());
        response.setOperation(record.getOperation());
        response.setCost(record.getCost());
        response.setCurrency(record.getCurrency());
        response.setCostCategory(record.getCostCategory());
        response.setPlatform(record.getPlatform());
        response.setService(record.getService());
        response.setTags(record.getTags());
        response.setSourcePayload(record.getSourcePayload());
        response.setBillingHash(record.getBillingHash());
        return response;
    }
}
