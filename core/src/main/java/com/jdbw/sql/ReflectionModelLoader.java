package com.jdbw.sql;

import com.jdbw.sql.exceptions.SqlException;

import java.beans.Transient;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class ReflectionModelLoader implements ModelLoader {

    @Override
    public <T> T load(ResultRow row, Class<T> type) throws SqlException {
        try {
            Constructor<T> constructor = type.getConstructor();
            if (constructor == null) {
                throw new ReflectiveOperationException("Expected empty constructor");
            }

            Object instance = constructor.newInstance();

            Field[] fields = type.getDeclaredFields();
            loadIntoFields(fields, instance, row);

            return type.cast(instance);
        } catch (ReflectiveOperationException e) {
            throw new SqlException(e);
        }
    }

    private void loadIntoFields(Field[] fields, Object instance, ResultRow resultRow) throws IllegalAccessException {
        for (Field field : fields) {
            if (field.isAnnotationPresent(Transient.class)) {
                continue;
            }

            String name = field.getName();
            Class<?> type = field.getType();

            ColumnValue value = resultRow.getValue(name);
            try {
                field.setAccessible(true);
                field.set(instance, value.getAs(type));
            } finally {
                field.setAccessible(false);
            }
        }
    }
}
