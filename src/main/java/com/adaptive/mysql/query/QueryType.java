package com.adaptive.mysql.query;

public enum QueryType {
    READ,   // SELECT
    WRITE,  // INSERT, UPDATE, DELETE
    OTHER   // DDL, etc.
}
