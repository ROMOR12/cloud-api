package com.roberto.cloud_api.application.processor;

public record ImportResult(int inserted, int duplicated, int failed) {

    public ImportResult add(ImportResult other) {
        return new ImportResult(
                this.inserted + other.inserted,
                this.duplicated + other.duplicated,
                this.failed + other.failed
        );
    }

    public static ImportResult empty() {
        return new ImportResult(0, 0, 0);
    }
}
