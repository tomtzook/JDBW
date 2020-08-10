package com.jdbw.sql.jdbc;

import com.jdbw.sql.SqlDatabase;
import com.jdbw.sql.Table;
import com.jdbw.sql.statements.SelectStatement;
import com.jdbw.sql.statements.generic.SelectBuilder;
import com.jdbw.sql.statements.generic.StatementFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcSqlDatabase implements SqlDatabase {

    private final Connection mConnection;
    private final StatementFactory mStatementFactory;

    public JdbcSqlDatabase(Connection connection, StatementFactory statementFactory) {
        mConnection = connection;
        mStatementFactory = statementFactory;
    }

    @Override
    public SelectStatement.Builder select(Table table) {
        return new SelectBuilder(mStatementFactory, table);
    }

    @Override
    public void close() throws IOException {
        try {
            mConnection.close();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
}
