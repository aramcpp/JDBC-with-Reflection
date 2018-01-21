package com.jdbc.test.model;

import com.jdbc.test.annotations.*;

/**
 * Created by aram on 1/14/2018.
 */
@DAOTable(name = "User")
public class User {
    @DAOField
    @DAOPrimary
    @DAOIdentity
    private Integer userid;

    @DAOField
    private String username;

    @DAOField
    private String password;

    @DAOField
    @DAONullable
    private String fullName;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
