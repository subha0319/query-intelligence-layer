package com.adaptive.mysql.query;

import com.adaptive.mysql.db.MySQLClient;
import com.adaptive.mysql.query.QueryParser;
import com.adaptive.mysql.query.QueryMetadata;
import com.adaptive.mysql.query.QueryFingerprint;
import com.adaptive.mysql.stats.QueryStatsStore;
import com.adaptive.mysql.cache.QueryCache;
import com.adaptive.mysql.cache.CacheStats;
import com.adaptive.mysql.indexadvisor.IndexAdvisor;

import java.util.List;

import com.adaptive.mysql.cache.CacheEntry;
import com.adaptive.mysql.dependency.DependencyGraph;

public class QueryExecutor {

    private final MySQLClient dbClient;
    private final QueryStatsStore statsStore;
    private final QueryCache cache;
    private final DependencyGraph dependencyGraph;
    private final CacheStats cacheStats;
    private final IndexAdvisor indexAdvisor;


    public QueryExecutor(MySQLClient dbClient,
                        QueryStatsStore statsStore,
                        QueryCache cache,
                        DependencyGraph dependencyGraph,
                        CacheStats cacheStats,
                        IndexAdvisor indexAdvisor) {

        this.dbClient = dbClient;
        this.statsStore = statsStore;
        this.cache = cache;
        this.dependencyGraph = dependencyGraph;
        this.cacheStats = cacheStats;
        this.indexAdvisor = indexAdvisor;
    }


    public ExecutionResult execute(String sql) {

        QueryType type = classify(sql);
        QueryMetadata metadata = QueryParser.parse(sql);
        String fingerprint = QueryFingerprint.hash(metadata.getNormalizedQuery());

        long start = System.nanoTime();
        Object result = null;

        try {
            if (type == QueryType.READ) {

                CacheEntry cached = cache.get(fingerprint);
                if (cached != null) {
                    // To be removed later
                    //System.out.println("[CACHE HIT] " + fingerprint.substring(0, 8));
                    cacheStats.recordHit();
                    result = cached.getResult();
                } else {
                    // To be removed later
                    //System.out.println("[CACHE MISS] " + fingerprint.substring(0, 8));
                    cacheStats.recordMiss();
                    result = dbClient.executeSelectInternal(sql);
                    cache.put(
                        fingerprint,
                        new CacheEntry((List<List<String>>) result)
                    );

                    // Register table dependencies
                    for (String table : metadata.getTables()) {
                        dependencyGraph.register(table, fingerprint);
                    }
                }

            } else if (type == QueryType.WRITE) {

                result = dbClient.executeWriteInternal(sql);

                // To be removed later
                //System.out.println("[WRITE] Invalidating cache");

                // Invalidate cache entries affected by this write
                for (String table : metadata.getTables()) {
                    for (String fp :
                        dependencyGraph.getDependentQueries(table)) {
                            // To be removed later
                            //System.out.println( "[INVALIDATE] " + fp.substring(0, 8) + " due to table " + table);

                        cache.invalidate(fp);
                        cacheStats.recordInvalidation();
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Query execution failed", e);
        }

        long end = System.nanoTime();
        long durationMs = (end - start) / 1_000_000;

        // TEMP: print to verify Phase 3 -- to be removed later
        // System.out.println("Normalized: " + metadata.getNormalizedQuery());
        // System.out.println("Fingerprint: " + fingerprint);
        // System.out.println("Tables: " + metadata.getTables());
        // System.out.println("Columns: " + metadata.getColumns());

        statsStore.record(fingerprint, durationMs);
        indexAdvisor.observe(
            metadata,
            statsStore.getAllStats().get(fingerprint)
        );
        // Only WHERE columns are considered for safe indexing advice

        return new ExecutionResult(type, durationMs, result);
    }

    private QueryType classify(String sql) {
        String trimmed = sql.trim().toUpperCase();

        if (trimmed.startsWith("SELECT")) {
            return QueryType.READ;
        }
        if (trimmed.startsWith("INSERT")
                || trimmed.startsWith("UPDATE")
                || trimmed.startsWith("DELETE")) {
            return QueryType.WRITE;
        }
        return QueryType.OTHER;
    }
}
