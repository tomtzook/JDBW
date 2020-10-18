package com.jdbw.sql.statements;

import com.jdbw.sql.Column;
import com.jdbw.sql.ColumnValue;
import com.jdbw.sql.Table;

import java.util.Collection;
import java.util.List;

public class InsertModel {

    private final Table mTable;
    private final List<Column> mColumns;
    private final List<Collection<? extends ColumnValue>> mValues;

    public InsertModel(Table table, List<Column> columns, List<Collection<? extends ColumnValue>> values) {
        mTable = table;
        mColumns = columns;
        mValues = values;
    }

    public Table getTable() {
        return mTable;
    }

    public List<Column> getColumns() {
        return mColumns;
    }

    public List<Collection<? extends ColumnValue>> getValues() {
        return mValues;
    }
}
