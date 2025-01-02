package it.almaviva.searen.planet.sensordata.utils;

import it.almaviva.searen.planet.sensordata.service.ConfigService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Table;

@ApplicationScoped
public class Utils {

    @Inject
    ConfigService configService;

    public String getTableName(Class<?> entityClass) {
        // Check if the @Table annotation is present
        Table tableAnnotation = entityClass.getAnnotation(Table.class);
        if (tableAnnotation != null && !tableAnnotation.name().isEmpty()) {
            return tableAnnotation.name();
        }
        // If @Table is not present or name is not specified, use the class name
        return entityClass.getSimpleName();
    }


    public String getSchemaName(Class<?> entityClass) {
        // Check if the @Table annotation is present
        Table tableAnnotation = entityClass.getAnnotation(Table.class);
        if (tableAnnotation != null && !tableAnnotation.schema().isEmpty()) {
            return tableAnnotation.schema();
        }

        return configService.getProperty("db.instance.schema","sensor_data");
    }
}
