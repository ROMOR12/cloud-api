package com.roberto.cloud_api.factory;

import com.roberto.cloud_api.parser.AuditParser;
import com.roberto.cloud_api.parser.BillingParser;
import com.roberto.cloud_api.parser.DataParser;
import org.springframework.stereotype.Component;

/**
 * This class works like a clever traffic cop.
 * When we upload a new CSV file, it looks at the very first line of text.
 * If it sees billing words it gives you the billing parser, and if it sees audit words it gives you the audit parser.
 */
@Component
public class ParserFactory {

    private final BillingParser billingParser;
    private final AuditParser auditParser;

    public ParserFactory(BillingParser billingParser, AuditParser auditParser) {
        this.billingParser = billingParser;
        this.auditParser = auditParser;
    }

    public DataParser<?> getParser(String header) {
        if (header.contains("invoice_id")){
            return billingParser;
        }
        if (header.contains("event_hash")){
            return auditParser;
        }
        throw new IllegalArgumentException("Unrecognized format: " + header);
    }
}
