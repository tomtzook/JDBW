package com.jdbw.sql.statements;

import com.jdbw.sql.Column;
import com.jdbw.sql.ColumnValue;
import com.jdbw.sql.Table;
import com.jdbw.sql.conditions.Condition;
import com.jdbw.sql.exceptions.SqlException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UpdateBuilder implements UpdateStatement.Builder {

    private final StatementFactory mStatementFactory;
    private final Table mTable;

    private final Map<Column, ColumnValue> mValues;
    private final Collection<Condition> mWhere;

    public UpdateBuilder(StatementFactory statementFactory, Table table) {
        mStatementFactory = statementFactory;
        mTable = table;

        mValues = new HashMap<>();
        mWhere = new ArrayList<>();
    }

    @Override
    public UpdateStatement.Builder set(Column column, ColumnValue value) {
        mValues.put(column, value);
        return this;
    }

    @Override
    public UpdateStatement.Builder set(Map<Column, ColumnValue> values) {
        mValues.putAll(values);
        return this;
    }

    @Override
    public UpdateStatement.Builder where(Collection<? extends Condition> conditions) {
        mWhere.addAll(conditions);
        return this;
    }

    @Override
    public UpdateStatement build() throws SqlException {
        UpdateModel model = new UpdateModel(mTable, mValues, mWhere);
        return mStatementFactory.createUpdate(model);
    }
}
