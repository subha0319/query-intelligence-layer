package com.adaptive.mysql.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class QueryCache {

    private final Map<String, CacheEntry> cache = new ConcurrentHashMap<>();

    public CacheEntry get(String fingerprint) {
        return cache.get(fingerprint);
    }

    public void put(String fingerprint, CacheEntry entry) {
        cache.put(fingerprint, entry);
    }

    public void invalidate(String fingerprint) {
        cache.remove(fingerprint);
    }
}
