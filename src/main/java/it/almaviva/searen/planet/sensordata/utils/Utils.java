package it.almaviva.searen.planet.sensordata.utils;

import jakarta.persistence.Table;

public class Utils {

    public static String getTableName(Class<?> entityClass) {
        // Check if the @Table annotation is present
        Table tableAnnotation = entityClass.getAnnotation(Table.class);
        if (tableAnnotation != null && !tableAnnotation.name().isEmpty()) {
            return tableAnnotation.name();
        }
        // If @Table is not present or name is not specified, use the class name
        return entityClass.getSimpleName();
    }
}
