package com.roberto.cloud_api.mapper;

import com.roberto.cloud_api.dto.response.AuditResponse;
import com.roberto.cloud_api.model.AuditRecord;

/**
 * Converter class.
 * It takes the heavy database object with all its internal details and copies just the data
 * we want to share into a clean response object ready to be sent to the user.
 */
public class AuditMapper {

    public static AuditResponse toResponse(AuditRecord record) {
        AuditResponse response = new AuditResponse();
        response.setId(record.getId());
        response.setTimestamp(record.getTimestamp());
        response.setEventDate(record.getEventDate());
        response.setTenant(record.getTenant());
        response.setProjectId(record.getProjectId());
        response.setPrincipalEmail(record.getPrincipalEmail());
        response.setServiceName(record.getServiceName());
        response.setMethodName(record.getMethodName());
        response.setSeverity(record.getSeverity());
        response.setSeverityLevel(record.getSeverityLevel());
        response.setEventHash(record.getEventHash());
        response.setSourceIp(record.getSourceIp());
        response.setUserAgent(record.getUserAgent());
        response.setIdentityType(record.getIdentityType());
        response.setIdentityName(record.getIdentityName());
        response.setRegion(record.getRegion());
        response.setErrorCode(record.getErrorCode());

        return response;
    }
}
