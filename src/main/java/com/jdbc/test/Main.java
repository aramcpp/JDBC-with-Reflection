package com.jdbc.test;

import com.jdbc.test.datasource.DataSourceProvider;
import com.jdbc.test.exceptions.SqlQueryGeneratorException;
import com.jdbc.test.generator.SqlQueryGenerator;
import com.jdbc.test.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by aram on 1/14/2018.
 */
public class Main {
    public static void main(String[] args) {
        SqlQueryGenerator generator = null;
        try {
            generator = new SqlQueryGenerator(User.class);
            System.out.println(generator.getCreateTableQuery());

            try (Connection conn = DataSourceProvider.getConnection()) {
                conn.prepareStatement(generator.getCreateTableQuery()).execute();

                PreparedStatement statement = conn.prepareStatement(generator.getInsertQuery());

                statement.setString(1, "a");
                statement.setString(2, "b");
                statement.setString(3, "c");

                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SqlQueryGeneratorException e) {
            e.printStackTrace();
        }
    }
}
