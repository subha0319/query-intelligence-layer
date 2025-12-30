package com.adaptive.mysql.stats;

public class QueryStats {

    private long count;
    private long totalLatencyMs;
    private long lastSeenEpochMs;

    public QueryStats() {
        this.count = 0;
        this.totalLatencyMs = 0;
    }

    public void recordExecution(long latencyMs) {
        count++;
        totalLatencyMs += latencyMs;
        lastSeenEpochMs = System.currentTimeMillis();
    }

    public long getCount() {
        return count;
    }

    public long getTotalLatencyMs() {
        return totalLatencyMs;
    }

    public long getAverageLatencyMs() {
        return count == 0 ? 0 : totalLatencyMs / count;
    }

    public long getLastSeenEpochMs() {
        return lastSeenEpochMs;
    }
}
