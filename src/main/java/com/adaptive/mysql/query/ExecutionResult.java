package com.adaptive.mysql.query;

public class ExecutionResult {

    private final QueryType queryType;
    private final long executionTimeMillis;
    private final Object result;

    public ExecutionResult(QueryType queryType,
                           long executionTimeMillis,
                           Object result) {
        this.queryType = queryType;
        this.executionTimeMillis = executionTimeMillis;
        this.result = result;
    }

    public QueryType getQueryType() {
        return queryType;
    }

    public long getExecutionTimeMillis() {
        return executionTimeMillis;
    }

    public Object getResult() {
        return result;
    }
}
