package com.jdbw.sql.statements;

import com.jdbw.sql.Table;
import com.jdbw.sql.conditions.Condition;
import com.jdbw.sql.exceptions.SqlException;

import java.util.ArrayList;
import java.util.Collection;

public class DeleteBuilder implements DeleteStatement.Builder {

    private final StatementFactory mStatementFactory;
    private final Table mTable;

    private final Collection<Condition> mWhere;

    public DeleteBuilder(StatementFactory statementFactory, Table table) {
        mStatementFactory = statementFactory;
        mTable = table;
        mWhere = new ArrayList<>();
    }

    @Override
    public DeleteStatement.Builder where(Collection<? extends Condition> conditions) {
        mWhere.addAll(conditions);
        return this;
    }

    @Override
    public DeleteStatement build() throws SqlException {
        DeleteModel model = new DeleteModel(mTable, mWhere);
        return mStatementFactory.createDelete(model);
    }
}
