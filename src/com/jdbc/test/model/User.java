package com.jdbc.test.model;

import com.jdbc.test.annotations.DAOField;
import com.jdbc.test.annotations.DAOTable;

/**
 * Created by aram on 1/14/2018.
 */
@DAOTable(name = "User")
public class User {
    @DAOField
    private String username;

    @DAOField
    private String password;

    @DAOField
    private int ba;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
