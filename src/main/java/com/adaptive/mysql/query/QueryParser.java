package com.adaptive.mysql.query;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryParser {

    private static final Pattern TABLE_PATTERN =
        Pattern.compile("\\bFROM\\s+(\\w+)|\\bJOIN\\s+(\\w+)|\\bUPDATE\\s+(\\w+)|\\bUPDATE\\s+(\\w+)",
                        Pattern.CASE_INSENSITIVE);

    private static final Pattern COLUMN_PATTERN =
        Pattern.compile("\\bWHERE\\s+([\\w.]+)|\\bAND\\s+([\\w.]+)",
                        Pattern.CASE_INSENSITIVE);

    public static QueryMetadata parse(String sql) {

        String normalized = QueryNormalizer.normalize(sql);

        Set<String> tables = new HashSet<>();
        Set<String> columns = new HashSet<>();

        Matcher tableMatcher = TABLE_PATTERN.matcher(normalized);
        while (tableMatcher.find()) {
            if (tableMatcher.group(1) != null)
                tables.add(tableMatcher.group(1));
            if (tableMatcher.group(2) != null)
                tables.add(tableMatcher.group(2));
            if (tableMatcher.group(3) != null)
                tables.add(tableMatcher.group(3));
        }

        Matcher columnMatcher = COLUMN_PATTERN.matcher(normalized);
        while (columnMatcher.find()) {
            if (columnMatcher.group(1) != null)
                columns.add(columnMatcher.group(1));
            if (columnMatcher.group(2) != null)
                columns.add(columnMatcher.group(2));
        }

        return new QueryMetadata(normalized, tables, columns);
    }
}
