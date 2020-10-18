package com.jdbw.sql.statements;

import com.jdbw.sql.Column;
import com.jdbw.sql.ColumnValue;
import com.jdbw.sql.conditions.Condition;
import com.jdbw.sql.exceptions.SqlException;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface UpdateStatement {

    interface Builder {
        default Builder set(Column column, Object value) {
            return set(column, ColumnValue.ofNullable(value));
        }
        default Builder setRaw(Map<Column, Object> values) {
            Map<Column, ColumnValue> valueMap = new HashMap<>();
            values.forEach((key, value) -> valueMap.put(key, ColumnValue.ofNullable(value)));

            return set(valueMap);
        }
        Builder set(Column column, ColumnValue value);
        Builder set(Map<Column, ColumnValue> values);

        default Builder where(Condition... conditions) {
            return where(Arrays.asList(conditions));
        }
        Builder where(Collection<? extends Condition> conditions);

        UpdateStatement build() throws SqlException;
    }

    void execute() throws SqlException;
}
