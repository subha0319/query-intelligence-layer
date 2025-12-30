package com.adaptive.mysql.indexadvisor;

import com.adaptive.mysql.query.QueryMetadata;
import com.adaptive.mysql.stats.QueryStats;

import java.util.*;

public class IndexAdvisor {

    private final Map<String, ColumnUsageStats> columnStats =
        new HashMap<>();

    public void observe(QueryMetadata metadata, QueryStats stats) {

        long impact =
            stats.getCount() * stats.getAverageLatencyMs();

        for (String column : metadata.getColumns()) {
            columnStats
                .computeIfAbsent(column, c -> new ColumnUsageStats())
                .addScore(impact);
        }
    }

    public List<String> recommendIndexes() {

        List<Map.Entry<String, ColumnUsageStats>> entries =
            new ArrayList<>(columnStats.entrySet());

        entries.sort((a, b) ->
            Long.compare(b.getValue().getScore(),
                         a.getValue().getScore()));

        List<String> recommendations = new ArrayList<>();

        for (Map.Entry<String, ColumnUsageStats> e : entries) {
            recommendations.add(
                "CREATE INDEX idx_" +
                e.getKey().toLowerCase() +
                " ON <TABLE>(" + e.getKey() + ");"
            );
        }

        return recommendations;
    }
}
