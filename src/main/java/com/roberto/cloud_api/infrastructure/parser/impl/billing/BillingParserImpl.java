package com.roberto.cloud_api.infrastructure.parser.impl.billing;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.roberto.cloud_api.domain.model.BillingRecord;
import com.roberto.cloud_api.infrastructure.parser.core.BillingParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
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
public class BillingParserImpl implements BillingParser {

    private final CSVParser csvParser = new CSVParserBuilder().withSeparator(',').build();
    private static final Logger log = LoggerFactory.getLogger(BillingParserImpl.class);
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS z");

    @Override
    public BillingRecord parse(String data) {
        try{
            String[] fields = csvParser.parseLine(data);

            if (fields == null || fields.length < 24) {
                return null;
            }
            BillingRecord billing = new BillingRecord();

            billing.setTenant(fields[0].trim());
            billing.setCloud(fields[1].trim());

            billing.setExportTime(LocalDateTime.parse(fields[2].trim(), TIME_FORMAT));
            billing.setInvoiceId(fields[3].trim());
            billing.setBillingPeriod(fields[4].trim());
            billing.setUsageDate(LocalDate.parse(fields[5].trim()));
            billing.setUsageStartTime(LocalDateTime.parse(fields[6].trim(), TIME_FORMAT));
            billing.setUsageEndTime(LocalDateTime.parse(fields[7].trim(), TIME_FORMAT));
            billing.setBillingAccountId(fields[8].trim());
            billing.setConsumerAccountId(fields[9].trim());
            billing.setProjectId(fields[10].trim());
            billing.setResourceId(fields[11].trim());
            billing.setRegion(fields[12].trim());
            billing.setServiceName(fields[13].trim());
            billing.setSkuName(fields[14].trim());
            billing.setOperation(fields[15].trim());

            String costStr = fields[16].trim();
            billing.setCost(costStr.isEmpty() ? BigDecimal.ZERO : new BigDecimal(costStr));

            billing.setCurrency(fields[17].trim());
            billing.setCostCategory(fields[18].trim());
            billing.setPlatform(fields[19].trim());
            billing.setService(fields[20].trim());
            billing.setTags(fields[21].trim());
            billing.setSourcePayload(fields[22].trim());
            billing.setBillingHash(fields[23].trim());

            return  billing;
        }
        catch (Exception e) {
            log.warn("Error parsing the billing line: {}", data);
            return null;
        }

    }
}
