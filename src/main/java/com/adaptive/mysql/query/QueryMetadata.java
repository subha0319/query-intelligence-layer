package com.adaptive.mysql.query;

import java.util.Set;

public class QueryMetadata {

    private final String normalizedQuery;
    private final Set<String> tables;
    private final Set<String> columns;

    public QueryMetadata(String normalizedQuery,
                         Set<String> tables,
                         Set<String> columns) {
        this.normalizedQuery = normalizedQuery;
        this.tables = tables;
        this.columns = columns;
    }

    public String getNormalizedQuery() {
        return normalizedQuery;
    }

    public Set<String> getTables() {
        return tables;
    }

    public Set<String> getColumns() {
        return columns;
    }
}
