package com.adaptive.mysql.indexadvisor;

public class ColumnUsageStats {

    private long score;

    public void addScore(long value) {
        score += value;
    }

    public long getScore() {
        return score;
    }
}
