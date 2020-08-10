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

    public ColumnValue getValue(String columnName) {
        return mColumns.get(columnName);
    }
}
