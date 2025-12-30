package com.adaptive.mysql.stats;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class QueryStatsStore {

    private final Map<String, QueryStats> statsMap =
        new ConcurrentHashMap<>();

    public void record(String fingerprint, long latencyMs) {
        statsMap
            .computeIfAbsent(fingerprint, f -> new QueryStats())
            .recordExecution(latencyMs);
    }

    public Map<String, QueryStats> getAllStats() {
        return statsMap;
    }
}
