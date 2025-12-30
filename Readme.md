# Adaptive Query Intelligence Layer for MySQL

An application-level optimization layer for MySQL that observes real query
workloads and improves performance using:

- Dependency-aware query result caching
- Query statistics collection
- Adaptive index recommendations

This project revisits ideas from the deprecated MySQL Query Cache and fixes its
limitations using modern data-structure–driven design.

---

## Features

- Centralized MySQL execution wrapper (JDBC)
- Query normalization and fingerprinting
- Query metadata extraction (tables, WHERE columns)
- Query statistics engine (frequency, latency)
- Dependency-aware SELECT result cache
- Safe cache invalidation on writes
- Cache hit/miss and invalidation metrics
- Adaptive index advisor (recommend-only)

---

## 🧱 Architecture Overview
```
Application / Simulator
|
v
QueryExecutor (Intelligence Layer)
├── Query Normalizer & Parser
├── Query Statistics Engine
├── Dependency-Aware Cache
└── Adaptive Index Advisor
|
v
MySQL
```

---

## 🛠 Tech Stack

- Java
- MySQL
- Maven
- JDBC (MySQL Connector/J)

---

## ⚙️ Prerequisites

- Java 11 or later
- Maven
- MySQL 8.x

---

## 🗄 Database Setup

Create a test database:

```sql
CREATE DATABASE optimizer_demo;
USE optimizer_demo;

CREATE TABLE users (
  id INT PRIMARY KEY,
  name VARCHAR(50),
  email VARCHAR(100),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE orders (
  id INT PRIMARY KEY,
  user_id INT,
  amount DECIMAL(10,2),
  status VARCHAR(20),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```
Insert sample data as needed

## How to Run

Clone the repository

Update database credentials in MySQLClient
(or set environment variables)

Run:
```
mvn clean compile
mvn exec:java -Dexec.mainClass="com.adaptive.mysql.App"
```

## Documentation

Design decisions: docs/design.md 

System architecture: docs/architecture.md

Common issues & fixes: docs/troubleshooting.md
