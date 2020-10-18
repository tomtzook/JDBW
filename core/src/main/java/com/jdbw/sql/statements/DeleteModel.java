package com.jdbw.sql.statements;

import com.jdbw.sql.Table;
import com.jdbw.sql.conditions.Condition;

import java.util.Collection;

public class DeleteModel {

    private final Table mTable;
    private final Collection<Condition> mWhere;

    public DeleteModel(Table table, Collection<Condition> where) {
        mTable = table;
        mWhere = where;
    }

    public Table getTable() {
        return mTable;
    }

    public Collection<Condition> getWhere() {
        return mWhere;
    }
}
