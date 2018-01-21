package com.jdbc.test.generator;

import com.jdbc.test.annotations.DAOField;
import com.jdbc.test.annotations.DAOTable;
import com.jdbc.test.model.User;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by aram on 1/14/2018.
 */
public class SqlQueryGenerator {
    private Class daoClass;

    public SqlQueryGenerator(Class daoClass) {
        if (!daoClass.isAnnotationPresent(DAOTable.class)) {
            throw new RuntimeException("Class must be annotated with DAOTable annotation");
        }

        this.daoClass = daoClass;
    }

    public String getCreateTableQuery() throws RuntimeException {
        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append(String.format("CREATE TABLE %s (", ((DAOTable) daoClass.getAnnotation(DAOTable.class)).name()));

        Field[] fields = Arrays.stream(User.class.getDeclaredFields())
                .filter((field) -> field.isAnnotationPresent(DAOField.class)).toArray(Field[]::new);

        for (int i = 0; i < fields.length; i++) {
            queryBuilder.append(String.format("%s %s", fields[i].getName(), getSqlTypeFromJavaType(fields[i].getType())));

            if (i != fields.length - 1) {
                queryBuilder.append(",");
            }
        }

        queryBuilder.append(")");

        return queryBuilder.toString();
    }

    private String getSqlTypeFromJavaType(Class type) {
        if (type.isPrimitive()) {
            switch (type.getTypeName()) {
                case "byte":
                    return "tinyint";
                case "short":
                    return "smallint";
                case "int":
                    return "int";
                case "long":
                    return "bigint";
                case "float":
                    return "float";
                case "double":
                    return "double";
                case "boolean":
                    return "bit";
                case "char":
                    return "char";
            }
        }

        if (type.equals(String.class)) {
            return "text";
        }

        throw new RuntimeException("Non primitive types are not supported at this moment");
    }
}
