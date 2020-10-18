package com.jdbw.sql;

import java.util.Map;

public class ResultRow {

    private final Map<String, ColumnValue> mColumns;

    public ResultRow(Map<String, ColumnValue> columns) {
        mColumns = columns;
    }

    public int getColumnCount() {
        return mColumns.size();
    }

    public boolean hasValue(Column column) {
        return hasValue(column.getName());
    }

    public boolean hasValue(String columnName) {
        return mColumns.containsKey(columnName);
    }

    public ColumnValue getValue(Column column) {
        return getValue(column.getName());
    }

    public ColumnValue getValue(String columnName) {
        return mColumns.get(columnName);
    }

    @Override
    public String toString() {
        return mColumns.toString();
    }
}
