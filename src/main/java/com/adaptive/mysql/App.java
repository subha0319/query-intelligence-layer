package com.adaptive.mysql;

import com.adaptive.mysql.db.MySQLClient;
import com.adaptive.mysql.query.QueryExecutor;
import com.adaptive.mysql.stats.QueryStatsStore;
import com.adaptive.mysql.cache.*;
import com.adaptive.mysql.dependency.DependencyGraph;
import com.adaptive.mysql.indexadvisor.IndexAdvisor;

public class App {
    public static void main(String[] args) {

        QueryStatsStore statsStore = new QueryStatsStore();
        CacheStats cacheStats = new CacheStats();
        IndexAdvisor indexAdvisor = new IndexAdvisor();

        QueryExecutor executor =
            new QueryExecutor(
                new MySQLClient(),
                statsStore,
                new QueryCache(),
                new DependencyGraph(),
                cacheStats,
                indexAdvisor
            );

        executor.execute("SELECT * FROM users WHERE id = 1");
        executor.execute("SELECT * FROM users WHERE id = 2");
        executor.execute("SELECT * FROM users WHERE id = 3");

        executor.execute("UPDATE users SET name='X' WHERE id=1");

        System.out.println("\n--- INDEX RECOMMENDATIONS ---");
        indexAdvisor.recommendIndexes()
            .forEach(System.out::println);
    }
}
