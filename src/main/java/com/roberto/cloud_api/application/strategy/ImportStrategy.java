package com.roberto.cloud_api.application.strategy;

public interface ImportStrategy<T> {
    boolean supports(Object record);
    boolean isDuplicate(T record);
    void accumulate(T record);
    void flush();
    void incrementDuplicated();
    int getInserted();
    int getDuplicated();
}
