package com.roberto.cloud_api.infrastructure.parser.audit;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.roberto.cloud_api.model.AuditRecord;
import com.roberto.cloud_api.parser.AuditParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This is the translator of our application.
 * It takes a single raw line of text from the CSV file, chops it up by commas,
 * and carefully turns it into a real Java database object.
 * If a line is corrupted or missing pieces, it safely returns null so the app never crashes.
 */
@Component
public class AuditParserImpl implements AuditParser {
    private final CSVParser csvParser = new CSVParserBuilder().withSeparator(',').build();
    private static final Logger log = LoggerFactory.getLogger(AuditParserImpl.class);
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS z");
    @Override
    public AuditRecord parse(String data) {
        try {
            String[] fields = csvParser.parseLine(data);

            if (fields == null || fields.length < 16) {
                return null;
            }

            AuditRecord audit = new AuditRecord();
            audit.setTimestamp(LocalDateTime.parse(fields[0].trim(), TIME_FORMAT));

            audit.setEventDate(LocalDate.parse(fields[1].trim()));
            audit.setTenant(fields[2].trim());
            audit.setProjectId(fields[3].trim());
            audit.setPrincipalEmail(fields[4].trim());
            audit.setServiceName(fields[5].trim());
            audit.setMethodName(fields[6].trim());
            audit.setSeverity(fields[7].trim());
            audit.setSeverityLevel(fields[8].trim());
            audit.setEventHash(fields[9].trim());
            audit.setSourceIp(fields[10].trim());
            audit.setUserAgent(fields[11].trim());
            audit.setIdentityType(fields[12].trim());
            audit.setIdentityName(fields[13].trim());
            audit.setRegion(fields[14].trim());
            audit.setErrorCode(fields[15].trim());

            return audit;
        }
        catch (Exception e) {
            log.warn("Error parsing the audit line: {}", data);
            return null;
        }

    }
}
