package com.jdbw.sql.statements;

import com.jdbw.sql.Column;
import com.jdbw.sql.ColumnValue;
import com.jdbw.sql.exceptions.SqlException;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public interface InsertStatement {

    interface Builder {
        default Builder column(Column... columns) {
            return column(Arrays.asList(columns));
        }
        Builder column(Collection<? extends Column> columns);

        default Builder valuesRaw(Object... values) {
            return valuesRaw(Arrays.asList(values));
        }
        default Builder valuesRaw(Collection<? extends Object> values) {
            return values(values.stream()
                    .map(ColumnValue::ofNullable)
                    .collect(Collectors.toList()));
        }

        default Builder values(ColumnValue... values) {
            return values(Arrays.asList(values));
        }
        Builder values(Collection<? extends ColumnValue> values);

        InsertStatement build() throws SqlException;
    }

    void execute() throws SqlException;
}
