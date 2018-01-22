package com.jdbc.test;

import com.jdbc.test.exceptions.SqlQueryGeneratorException;
import com.jdbc.test.generator.SqlQueryGenerator;
import com.jdbc.test.model.User;

/**
 * Created by aram on 1/14/2018.
 */
public class Main {
    public static void main(String[] args) {
        SqlQueryGenerator generator = null;
        try {
            generator = new SqlQueryGenerator(User.class);
            System.out.println(generator.getCreateTableQuery());
        } catch (SqlQueryGeneratorException e) {
            e.printStackTrace();
        }
    }
}
