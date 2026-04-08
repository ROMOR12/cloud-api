package com.roberto.cloud_api.util;

import org.springframework.stereotype.Component;

@Component
public class DataCleaner {

    public boolean isGarbage(String line) {
        // If the line is empty or null, it is garbage
        if (line == null || line.trim().isEmpty()) {
            return true;
        }
        return false;
    }

    // Clean up the text so the parser doesn't get confused
    public String sanitizeLine(String line) {
        // If there's no text, just return an empty string
        if (line == null) {
            return "";
        }

        String cleaned = line;

        // Remove invisible weird characters and Windows line breaks (\r)
        cleaned = cleaned.replace("\uFEFF", "").replace("\r", "");

        // If the line starts with a quote, just delete it
        if (cleaned.startsWith("\"")) {
            cleaned = cleaned.substring(1);
        }

        // Fix double quotes ("") into single ones (")
        cleaned = cleaned.replace("\"\"", "\"");

        // Fix messy separators like "; "
        cleaned = cleaned.replace("\";\"", ";");

        // Use a Regex to delete quotes or spaces at the very end of the line
        cleaned = cleaned.replaceAll("[\";\\s]+$", "");

        return cleaned;
    }
}
