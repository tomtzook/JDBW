package com.jdbw.sql.statements;

import com.jdbw.sql.Column;
import com.jdbw.sql.ColumnValue;
import com.jdbw.sql.Table;
import com.jdbw.sql.conditions.Condition;

import java.util.Collection;
import java.util.Map;

public class UpdateModel {

    private final Table mTable;
    private final Map<Column, ColumnValue> mValues;
    private final Collection<Condition> mWhere;

    public UpdateModel(Table table, Map<Column, ColumnValue> values, Collection<Condition> where) {
        mTable = table;
        mValues = values;
        mWhere = where;
    }

    public Table getTable() {
        return mTable;
    }

    public Map<Column, ColumnValue> getValues() {
        return mValues;
    }

    public Collection<Condition> getWhere() {
        return mWhere;
    }
}
