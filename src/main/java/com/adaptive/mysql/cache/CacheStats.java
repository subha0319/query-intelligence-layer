package com.adaptive.mysql.cache;

import java.util.concurrent.atomic.AtomicLong;

public class CacheStats {

    private final AtomicLong hits = new AtomicLong();
    private final AtomicLong misses = new AtomicLong();
    private final AtomicLong invalidations = new AtomicLong();
    // AtomicLong is thread-safe, zero locking complexity

    public void recordHit() {
        hits.incrementAndGet();
    }

    public void recordMiss() {
        misses.incrementAndGet();
    }

    public void recordInvalidation() {
        invalidations.incrementAndGet();
    }

    public long getHits() {
        return hits.get();
    }

    public long getMisses() {
        return misses.get();
    }

    public long getInvalidations() {
        return invalidations.get();
    }

    public double getHitRatio() {
        long total = hits.get() + misses.get();
        return total == 0 ? 0.0 : (double) hits.get() / total;
    }
}
