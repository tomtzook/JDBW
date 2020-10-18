package com.jdbw.sql.jdbc;

import com.jdbw.sql.ReflectionModelLoader;
import com.jdbw.sql.SqlDatabase;
import com.jdbw.sql.Table;
import com.jdbw.sql.conditions.ConditionFactory;
import com.jdbw.sql.exceptions.SqlException;
import com.jdbw.sql.jdbc.conditions.JdbcConditionFactory;
import com.jdbw.sql.jdbc.meta.JdbcDatabaseMeta;
import com.jdbw.sql.jdbc.statements.JdbcStatementFactory;
import com.jdbw.sql.meta.DatabaseMeta;
import com.jdbw.sql.statements.DeleteBuilder;
import com.jdbw.sql.statements.DeleteStatement;
import com.jdbw.sql.statements.InsertBuilder;
import com.jdbw.sql.statements.InsertStatement;
import com.jdbw.sql.statements.SelectStatement;
import com.jdbw.sql.statements.SelectBuilder;
import com.jdbw.sql.statements.StatementFactory;
import com.jdbw.sql.statements.UpdateBuilder;
import com.jdbw.sql.statements.UpdateStatement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcSqlDatabase implements SqlDatabase {

    private final Connection mConnection;
    private final StatementFactory mStatementFactory;
    private final ConditionFactory mConditionFactory;
    private final JdbcDatabaseMeta mMeta;

    public JdbcSqlDatabase(Connection connection, StatementFactory statementFactory, ConditionFactory conditionFactory) {
        mConnection = connection;
        mStatementFactory = statementFactory;
        mConditionFactory = conditionFactory;
        mMeta = new JdbcDatabaseMeta(connection, mConditionFactory);
    }

    public JdbcSqlDatabase(Connection connection) {
        this(connection, new JdbcStatementFactory(connection, new QueryBuilder(), new SqlObjectAdapter(), new ResultRowParser(), new ReflectionModelLoader()), new JdbcConditionFactory());
    }

    public JdbcSqlDatabase(ConnectionConfig connectionConfig, StatementFactory statementFactory, ConditionFactory conditionFactory) throws SqlException {
        this(openConnection(connectionConfig), statementFactory, conditionFactory);
    }

    public JdbcSqlDatabase(ConnectionConfig connectionConfig) throws SqlException {
        this(openConnection(connectionConfig));
    }

    @Override
    public DatabaseMeta meta() throws SqlException {
        return mMeta;
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
    public InsertStatement.Builder insert(Table table) {
        return new InsertBuilder(mStatementFactory, table);
    }

    @Override
    public UpdateStatement.Builder update(Table table) {
        return new UpdateBuilder(mStatementFactory, table);
    }

    @Override
    public DeleteStatement.Builder delete(Table table) {
        return new DeleteBuilder(mStatementFactory, table);
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
