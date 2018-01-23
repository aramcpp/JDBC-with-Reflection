package com.jdbc.test.generator;

import com.jdbc.test.annotations.*;
import com.jdbc.test.exceptions.SqlQueryGeneratorException;
import com.jdbc.test.model.User;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by aram on 1/14/2018.
 */
public class SqlQueryGenerator {
    private Class daoClass;

    public SqlQueryGenerator(Class daoClass) throws SqlQueryGeneratorException {
        if (!daoClass.isAnnotationPresent(DAOTable.class)) {
            throw new SqlQueryGeneratorException("Class must be annotated with DAOTable annotation");
        }

        this.daoClass = daoClass;
    }

    public String getCreateTableQuery() throws SqlQueryGeneratorException {
        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append(String.format("CREATE TABLE %s (", ((DAOTable) daoClass.getAnnotation(DAOTable.class)).name()));

        Field[] fields = Arrays.stream(User.class.getDeclaredFields())
                .filter((field) -> field.isAnnotationPresent(DAOField.class)).toArray(Field[]::new);

        boolean isPrimaryKeySet = false;

        for (int i = 0; i < fields.length; i++) {
            queryBuilder.append(String.format("%s %s", fields[i].getName(), getSqlTypeFromJavaType(fields[i].getType())));

            if (!fields[i].isAnnotationPresent(DAONullable.class)) {
                queryBuilder.append(" NOT NULL");
            }

            if (fields[i].isAnnotationPresent(DAOPrimary.class)) {
                if (isPrimaryKeySet) {
                    throw new SqlQueryGeneratorException("There can be at most one primary key");
                }

                isPrimaryKeySet = true;

                queryBuilder.append(" PRIMARY KEY");
            }

            if (fields[i].isAnnotationPresent(DAOIdentity.class)) {
                if (!getSqlTypeFromJavaType(fields[i].getType()).contains("int")) {
                    throw new SqlQueryGeneratorException("Only integer columns can be identity");
                }

                queryBuilder.append(" AUTO_INCREMENT");
            }

            if (i != fields.length - 1) {
                queryBuilder.append(",");
            }
        }

        queryBuilder.append(")");

        return queryBuilder.toString();
    }

    public String getInsertQuery() {
        Field[] fields = Arrays.stream(User.class.getDeclaredFields())
                .filter((field) -> field.isAnnotationPresent(DAOField.class)).toArray(Field[]::new);

        String columns = Arrays.stream(fields)
                .filter((x) -> !x.isAnnotationPresent(DAOIdentity.class))
                .map((x) -> x.getName()).collect(Collectors.joining(", "));

        String values = Arrays.stream(fields)
                .filter((x) -> !x.isAnnotationPresent(DAOIdentity.class))
                .map((x) -> "?").collect(Collectors.joining(", "));

        return String.format("INSERT INTO %s (%s) VALUES (%s)", ((DAOTable) daoClass.getAnnotation(DAOTable.class)).name(), columns, values);
    }

    private String getSqlTypeFromJavaType(Class type) {
        switch (type.getTypeName()) {
            case "byte":
            case "java.lang.Byte":
                return "tinyint";
            case "short":
            case "java.lang.Short":
                return "smallint";
            case "int":
            case "java.lang.Integer":
                return "int";
            case "long":
            case "java.lang.Long":
                return "bigint";
            case "float":
            case "java.lang.Float":
                return "float";
            case "double":
            case "java.lang.Double":
                return "double";
            case "boolean":
            case "java.lang.Boolean":
                return "bit";
            case "char":
            case "java.lang.Character":
                return "char";
            case "java.lang.String":
                return "text";
        }

        throw new RuntimeException("Only primitives their wrappers and String are supported at this moment");
    }
}
