package com.jdbc.test.dao.connection;

import com.jdbc.test.exceptions.DBConnectionException;

import java.sql.Connection;
import java.util.Properties;

public class DBConnector {
    private String dbType;
    private String dbHost;
    private Integer dbPort;
    private String dbName;

    public DBConnector(Properties dbProperties) throws DBConnectionException {
        if (!dbProperties.containsKey("dbType")) {
            throw new DBConnectionException("You must specify DB type");
        }

        if (!dbProperties.containsKey("dbHost")) {
            throw new DBConnectionException("You must specify DB host");
        }

        if (!dbProperties.containsKey("dbPort")) {
            throw new DBConnectionException("You must specify DB port");
        }

        if (!dbProperties.containsKey("dbName")) {
            throw new DBConnectionException("You must specify DB name");
        }

        dbType = dbProperties.getProperty("dbType");
        dbHost = dbProperties.getProperty("dbHost");
        dbPort = Integer.parseInt(dbProperties.getProperty("dbPost"));
        dbName = dbProperties.getProperty("dbName");
    }

    public Connection getConnection() {

    }
}
