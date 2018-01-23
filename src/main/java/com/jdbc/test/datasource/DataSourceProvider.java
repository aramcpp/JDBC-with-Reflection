package com.jdbc.test.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceProvider {
    private static HikariConfig config = new HikariConfig("db.properties");
    private static HikariDataSource dataSource = new HikariDataSource(config);

    private DataSourceProvider() {
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
