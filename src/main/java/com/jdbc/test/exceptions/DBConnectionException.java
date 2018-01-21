package com.jdbc.test.exceptions;

public class DBConnectionException extends Exception {
    public DBConnectionException(String e) {
        super(e);
    }
}
