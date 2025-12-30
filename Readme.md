# Adaptive Query Intelligence Layer for MySQL

This project implements an application-level query optimization layer for MySQL.
It combines dependency-aware query caching with query statistics tracking to
reduce redundant reads and safely invalidate cache entries on writes.

## Features Implemented
- JDBC-based MySQL execution wrapper
- Query normalization and fingerprinting
- Query metadata extraction (tables, columns)
- Query statistics engine (frequency, latency)
- Dependency-aware query result cache
- Cache hit/miss and invalidation metrics

## Tech Stack
- Java
- MySQL
- Maven

## How to Run
1. Create a MySQL database `optimizer_demo`
2. Update DB credentials
3. Run:
   ```bash
   mvn clean compile
   mvn exec:java -Dexec.mainClass="com.adaptive.mysql.App"
