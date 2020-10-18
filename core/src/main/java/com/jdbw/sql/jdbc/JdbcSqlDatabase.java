package com.jdbw.sql.jdbc;

import com.jdbw.sql.SqlDatabase;
import com.jdbw.sql.Table;
import com.jdbw.sql.conditions.ConditionFactory;
import com.jdbw.sql.exceptions.SqlException;
import com.jdbw.sql.jdbc.conditions.JdbcConditionFactory;
import com.jdbw.sql.jdbc.statements.JdbcStatementFactory;
import com.jdbw.sql.statements.SelectStatement;
import com.jdbw.sql.statements.generic.SelectBuilder;
import com.jdbw.sql.statements.generic.StatementFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcSqlDatabase implements SqlDatabase {

    private final Connection mConnection;
    private final StatementFactory mStatementFactory;
    private final ConditionFactory mConditionFactory;

    public JdbcSqlDatabase(Connection connection, StatementFactory statementFactory, ConditionFactory conditionFactory) {
        mConnection = connection;
        mStatementFactory = statementFactory;
        mConditionFactory = conditionFactory;
    }

    public JdbcSqlDatabase(Connection connection) {
        this(connection, new JdbcStatementFactory(connection, new QueryBuilder(), new SqlObjectAdapter(), new ResultRowParser()), new JdbcConditionFactory());
    }

    public JdbcSqlDatabase(ConnectionConfig connectionConfig, StatementFactory statementFactory, ConditionFactory conditionFactory) throws SqlException {
        this(openConnection(connectionConfig), statementFactory, conditionFactory);
    }

    public JdbcSqlDatabase(ConnectionConfig connectionConfig) throws SqlException {
        this(openConnection(connectionConfig));
    }

    @Override
    public ConditionFactory conditions() {
        return mConditionFactory;
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

    private static Connection openConnection(ConnectionConfig connectionConfig) throws SqlException {
        try {
            Class.forName(connectionConfig.getDriver());
            return DriverManager.getConnection(connectionConfig.getUrl(),
                    connectionConfig.getUsername(), connectionConfig.getPassword());
        } catch (SQLException | ClassNotFoundException e) {
            throw new SqlException(e);
        }
    }
}
