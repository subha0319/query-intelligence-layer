package com.adaptive.mysql.dependency;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DependencyGraph {

    private final Map<String, Set<String>> tableToQueries =
        new ConcurrentHashMap<>();

    public void register(String table, String fingerprint) {
        tableToQueries
            .computeIfAbsent(table, t -> ConcurrentHashMap.newKeySet())
            .add(fingerprint);
    }

    public Set<String> getDependentQueries(String table) {
        return tableToQueries.getOrDefault(
            table, Collections.emptySet());
    }

    public void removeFingerprint(String fingerprint) {
        for (Set<String> fps : tableToQueries.values()) {
            fps.remove(fingerprint);
        }
    }
}
