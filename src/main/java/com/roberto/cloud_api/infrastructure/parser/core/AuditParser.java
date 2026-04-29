package com.roberto.cloud_api.infrastructure.parser.core;

import com.roberto.cloud_api.domain.model.AuditRecord;

/**
 * A simple contract rule.
 * It just tells the application that any class implementing this interface
 * must know exactly how to parse this specific type of record.
 */
public interface AuditParser extends DataParser<AuditRecord> {
    @Override
    default boolean supports(String headerLine){
        return headerLine.contains("event_hash");
    }
}
