package com.roberto.cloud_api.infrastructure.parser.core;

/**
 * This is our master template for all parsers.
 * It simply states a rule: no matter what kind of data we are dealing with,
 * the class must have a method to turn a raw line of text into a real Java object.
 */
public interface DataParser<T> {
    T parse(String dataLine);
    boolean supports(String headerLine);
}

