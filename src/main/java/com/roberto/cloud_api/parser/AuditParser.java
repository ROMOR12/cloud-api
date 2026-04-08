package com.roberto.cloud_api.parser;

import com.roberto.cloud_api.model.AuditRecord;

/**
 * A simple contract rule.
 * It just tells the application that any class implementing this interface
 * must know exactly how to parse this specific type of record.
 */
public interface AuditParser extends DataParser<AuditRecord> {
}
