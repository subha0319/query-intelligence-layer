package com.adaptive.mysql.query;

public class QueryNormalizer {

    /**
     * Normalizes SQL by:
     * - removing extra whitespace
     * - converting to upper case
     * - replacing literals with ?
     */
    public static String normalize(String sql) {

        // Replace string literals
        String normalized = sql.replaceAll("'[^']*'", "?");

        // Replace numeric literals
        normalized = normalized.replaceAll("\\b\\d+\\b", "?");

        // Normalize whitespace
        normalized = normalized.replaceAll("\\s+", " ").trim();

        return normalized.toUpperCase();
    }
}
