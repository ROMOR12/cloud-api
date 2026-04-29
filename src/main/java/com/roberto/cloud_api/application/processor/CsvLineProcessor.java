package com.roberto.cloud_api.application.processor;

import com.roberto.cloud_api.application.strategy.ImportStrategy;
import com.roberto.cloud_api.infrastructure.parser.factory.ParserFactory;
import com.roberto.cloud_api.infrastructure.parser.core.DataParser;
import com.roberto.cloud_api.shared.util.DataCleaner;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@Component
@RequestScope
public class CsvLineProcessor {

    private final ParserFactory parserFactory;
    private final DataCleaner dataCleaner;

    public CsvLineProcessor(ParserFactory parserFactory, DataCleaner dataCleaner) {
        this.parserFactory = parserFactory;
        this.dataCleaner = dataCleaner;
    }

    public ImportResult process(BufferedReader reader,
                                List<ImportStrategy<?>> strategies) throws IOException {
        int failed = 0;
        boolean isFirstLine = true;
        DataParser<?> parser = null;

        String line;
        while ((line = reader.readLine()) != null) {
            if (dataCleaner.isGarbage(line)) continue;

            if (isFirstLine) {
                parser = parserFactory.getParser(line);
                isFirstLine = false;
                continue;
            }

            line = dataCleaner.sanitizeLine(line);
            if (line.isEmpty()) continue;

            Object record = parser.parse(line);
            if (record == null) { failed++; continue; }

            boolean handled = false;
            for (ImportStrategy<?> strategy : strategies) {
                if (strategy.supports(record)) {
                    handleRecord(strategy, record);
                    handled = true;
                    break;
                }
            }
            if (!handled) failed++;
        }

        strategies.forEach(ImportStrategy::flush);

        int inserted = strategies.stream().mapToInt(ImportStrategy::getInserted).sum();
        int duplicated = strategies.stream().mapToInt(ImportStrategy::getDuplicated).sum();

        return new ImportResult(inserted, duplicated, failed);
    }

    @SuppressWarnings("unchecked")
    private <T> void handleRecord(ImportStrategy<T> strategy, Object record) {
        T typed = (T) record;
        if (strategy.isDuplicate(typed)) {
            strategy.incrementDuplicated();
        } else {
            strategy.accumulate(typed);
        }
    }
}