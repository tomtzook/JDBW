package com.jdbw.sql.statements;

import com.jdbw.sql.Column;
import com.jdbw.sql.conditions.Condition;
import com.jdbw.sql.Table;

import java.util.Collection;

public class SelectModel {

    private final Table mTable;
    private final Collection<Column> mSelect;
    private final Collection<Condition> mWhere;

    public SelectModel(Table table, Collection<Column> select, Collection<Condition> where) {
        mTable = table;
        mSelect = select;
        mWhere = where;
    }

    public Table getTable() {
        return mTable;
    }

    public Collection<Column> getSelect() {
        return mSelect;
    }

    public Collection<Condition> getWhere() {
        return mWhere;
    }
}
