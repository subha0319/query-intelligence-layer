package com.adaptive.mysql.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLClient {

    private static final String URL = "jdbc:mysql://localhost:3306/optimizer_demo?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "optimizer_user";
    private static final String PASSWORD = "optimizer_pass";
    
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // SELECT queries
    public List<List<String>> executeSelectInternal(String sql)
            throws SQLException {

        List<List<String>> rows = new ArrayList<>();

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            int colCount = rs.getMetaData().getColumnCount();

            while (rs.next()) {
                List<String> row = new ArrayList<>();
                for (int i = 1; i <= colCount; i++) {
                    row.add(rs.getString(i));
                }
                rows.add(row);
            }
        }
        return rows;
    }

    // INSERT / UPDATE / DELETE
    public int executeWriteInternal(String sql)
            throws SQLException {

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            return stmt.executeUpdate(sql);
        }
    }
}
