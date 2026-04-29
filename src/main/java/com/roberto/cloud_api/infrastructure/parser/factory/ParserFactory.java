package com.roberto.cloud_api.infrastructure.parser.factory;

import com.roberto.cloud_api.infrastructure.parser.core.DataParser;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * This class works like a clever traffic cop.
 * When we upload a new CSV file, it looks at the very first line of text.
 * If it sees billing words it gives you the billing parser, and if it sees audit words it gives you the audit parser.
 */
@Component
public class ParserFactory {

    private final List<DataParser<?>> parsers;

    public ParserFactory(List<DataParser<?>> parsers) {
        this.parsers = parsers;
    }

    public DataParser<?> getParser(String headerLine) {
        for(DataParser<?> parser : parsers){
            if(parser.supports(headerLine)){
                return parser;
            }
        }
        throw new IllegalArgumentException("Unrecognized format: " + headerLine);
    }
}
