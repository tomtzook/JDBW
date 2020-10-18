package com.jdbw.sql.statements;

import com.jdbw.sql.Column;
import com.jdbw.sql.ColumnValue;
import com.jdbw.sql.Table;
import com.jdbw.sql.exceptions.SqlException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InsertBuilder implements InsertStatement.Builder {

    private final StatementFactory mStatementFactory;
    private final Table mTable;

    private final List<Column> mColumns;
    private final List<Collection<? extends ColumnValue>> mValues;

    public InsertBuilder(StatementFactory statementFactory, Table table) {
        mStatementFactory = statementFactory;
        mTable = table;
        mColumns = new ArrayList<>();
        mValues = new ArrayList<>();
    }

    @Override
    public InsertStatement.Builder column(Collection<? extends Column> columns) {
        mColumns.addAll(columns);
        return this;
    }

    @Override
    public InsertStatement.Builder values(Collection<? extends ColumnValue> values) {
        mValues.add(values);
        return this;
    }

    @Override
    public InsertStatement build() throws SqlException {
        InsertModel model = new InsertModel(mTable, mColumns, mValues);
        return mStatementFactory.createInsert(model);
    }
}
