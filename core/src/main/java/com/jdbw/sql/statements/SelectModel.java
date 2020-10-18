package com.jdbw.sql.statements;

import com.jdbw.sql.Column;
import com.jdbw.sql.Limit;
import com.jdbw.sql.Order;
import com.jdbw.sql.conditions.Condition;
import com.jdbw.sql.Table;

import java.util.Collection;
import java.util.Map;

public class SelectModel {

    private final Table mTable;
    private final Collection<Column> mSelect;
    private final Collection<Condition> mWhere;
    private final Collection<Column> mGroup;
    private final Map<Column, Order> mOrder;
    private final Limit mLimit;

    public SelectModel(Table table, Collection<Column> select, Collection<Condition> where,
                       Collection<Column> group, Map<Column, Order> order, Limit limit) {
        mTable = table;
        mSelect = select;
        mWhere = where;
        mGroup = group;
        mOrder = order;
        mLimit = limit;
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

    public Collection<Column> getGroup() {
        return mGroup;
    }

    public Map<Column, Order> getOrder() {
        return mOrder;
    }

    public Limit getLimit() {
        return mLimit;
    }
}
