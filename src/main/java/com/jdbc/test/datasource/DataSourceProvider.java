package com.jdbc.test.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataSourceProvider {
    private static HikariConfig config;
    private static HikariDataSource dataSource;

    static {
        try {
            Properties properties = new Properties();
            properties.load(DataSourceProvider.class.getClassLoader().getResourceAsStream("db.properties"));

            config = new HikariConfig(properties);

            dataSource = new HikariDataSource(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DataSourceProvider() {
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
