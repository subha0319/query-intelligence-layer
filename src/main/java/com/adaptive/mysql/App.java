package com.adaptive.mysql;

import com.adaptive.mysql.db.MySQLClient;
import com.adaptive.mysql.query.QueryExecutor;
import com.adaptive.mysql.stats.QueryStatsStore;
import com.adaptive.mysql.cache.QueryCache;
import com.adaptive.mysql.dependency.DependencyGraph;
import com.adaptive.mysql.cache.CacheStats;

public class App {
    public static void main(String[] args) {

        CacheStats cacheStats = new CacheStats();

        QueryExecutor executor =
            new QueryExecutor(
                new MySQLClient(),
                new QueryStatsStore(),
                new QueryCache(),
                new DependencyGraph(),
                cacheStats
            );

        executor.execute("SELECT * FROM users");
        executor.execute("SELECT * FROM users");
        executor.execute("UPDATE users SET name='X' WHERE id=1");
        executor.execute("SELECT * FROM users");

        System.out.println("\n--- CACHE STATS ---");
        System.out.println("Hits          : " + cacheStats.getHits());
        System.out.println("Misses        : " + cacheStats.getMisses());
        System.out.println("Invalidations : " + cacheStats.getInvalidations());
        System.out.println("Hit Ratio     : " + cacheStats.getHitRatio());
    }
}
