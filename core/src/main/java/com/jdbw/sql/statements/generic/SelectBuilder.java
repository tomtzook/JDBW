package com.jdbw.sql.statements.generic;

import com.jdbw.sql.Column;
import com.jdbw.sql.Condition;
import com.jdbw.sql.GroupBy;
import com.jdbw.sql.Limit;
import com.jdbw.sql.OrderBy;
import com.jdbw.sql.Table;
import com.jdbw.sql.exceptions.SqlException;
import com.jdbw.sql.statements.SelectStatement;

import java.util.ArrayList;
import java.util.Collection;

public class SelectBuilder implements SelectStatement.Builder {

    private final StatementFactory mStatementFactory;

    private final Table mTable;
    private final Collection<Column> mSelect;
    private final Collection<Condition> mWhere;

    public SelectBuilder(StatementFactory statementFactory, Table table) {
        mStatementFactory = statementFactory;
        mTable = table;
        mSelect = new ArrayList<>();
        mWhere = new ArrayList<>();
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
    public SelectStatement.Builder orderBy(GroupBy groupBy) {
        throw new UnsupportedOperationException();
    }
    @Override
    public SelectStatement.Builder orderBy(OrderBy orderBy) {
        throw new UnsupportedOperationException();
    }
    @Override
    public SelectStatement.Builder limit(Limit limit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SelectStatement build() throws SqlException {
        SelectModel model = new SelectModel(mTable, mSelect, mWhere);
        return mStatementFactory.createSelect(model);
    }
}
