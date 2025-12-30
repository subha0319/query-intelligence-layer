package com.adaptive.mysql.cache;

import java.util.List;

public class CacheEntry {

    private final List<List<String>> result;
    private final long cachedAtEpochMs;

    public CacheEntry(List<List<String>> result) {
        this.result = result;
        this.cachedAtEpochMs = System.currentTimeMillis();
    }

    public List<List<String>> getResult() {
        return result;
    }

    public long getCachedAtEpochMs() {
        return cachedAtEpochMs;
    }
}
