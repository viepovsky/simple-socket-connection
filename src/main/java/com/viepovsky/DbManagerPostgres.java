package com.viepovsky;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

enum DbManagerPostgres {
    INSTANCE;
    private final Connection connection;
    private static final String POSTGRES_URL = "jdbc:postgresql://localhost:5432/viepovsky";

    DbManagerPostgres() {
        Properties connectionProperties = new Properties();
        connectionProperties.put("user", "viepovsky");
        connectionProperties.put("password", "viepovsky");
        try {
            connection = DriverManager.getConnection(POSTGRES_URL, connectionProperties);
            System.out.println("Connected to Postgres db.");
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    static DbManagerPostgres getInstance() {
        return INSTANCE;
    }

    Connection getConnection() {
        return connection;
    }
}
