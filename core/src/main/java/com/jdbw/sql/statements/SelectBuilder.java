package com.jdbw.sql.statements;

import com.jdbw.sql.Column;
import com.jdbw.sql.Limit;
import com.jdbw.sql.Order;
import com.jdbw.sql.Table;
import com.jdbw.sql.conditions.Condition;
import com.jdbw.sql.exceptions.SqlException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SelectBuilder implements SelectStatement.Builder {

    private final StatementFactory mStatementFactory;

    private final Table mTable;
    private final Collection<Column> mSelect;
    private final Collection<Condition> mWhere;
    private final Collection<Column> mGroup;
    private final Map<Column, Order> mOrder;
    private Limit mLimit;

    public SelectBuilder(StatementFactory statementFactory, Table table) {
        mStatementFactory = statementFactory;
        mTable = table;
        mSelect = new ArrayList<>();
        mWhere = new ArrayList<>();
        mGroup = new ArrayList<>();
        mOrder = new HashMap<>();
        mLimit = null;
    }

    @Override
    public SelectStatement.Builder select(Collection<? extends Column> columns) {
        mSelect.addAll(columns);
        return this;
    }

    @Override
    public SelectStatement.Builder where(Collection<? extends Condition> conditions) {
        mWhere.addAll(conditions);
        return this;
    }

    @Override
    public SelectStatement.Builder groupBy(Collection<? extends Column> columns) {
        mGroup.addAll(columns);
        return this;
    }

    @Override
    public SelectStatement.Builder orderBy(Column column, Order order) {
        mOrder.put(column, order);
        return this;
    }

    @Override
    public SelectStatement.Builder orderBy(Order order, Column... columns) {
        for (Column column : columns) {
            orderBy(column, order);
        }
        return this;
    }

    @Override
    public SelectStatement.Builder orderBy(Map<Column, Order> order) {
        mOrder.putAll(order);
        return this;
    }

    @Override
    public SelectStatement.Builder limit(Limit limit) {
        mLimit = limit;
        return this;
    }

    @Override
    public SelectStatement build() throws SqlException {
        SelectModel model = new SelectModel(mTable, mSelect, mWhere, mGroup, mOrder, mLimit);
        return mStatementFactory.createSelect(model);
    }
}
