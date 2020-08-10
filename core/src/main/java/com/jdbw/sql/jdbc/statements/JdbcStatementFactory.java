package com.jdbw.sql.jdbc.statements;

import com.jdbw.sql.exceptions.SqlException;
import com.jdbw.sql.statements.SelectStatement;
import com.jdbw.sql.statements.generic.SelectModel;
import com.jdbw.sql.statements.generic.SqlBuilder;
import com.jdbw.sql.statements.generic.StatementFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcStatementFactory implements StatementFactory {

    private final Connection mConnection;
    private final SqlBuilder mSqlBuilder;

    public JdbcStatementFactory(Connection connection, SqlBuilder sqlBuilder) {
        mConnection = connection;
        mSqlBuilder = sqlBuilder;
    }

    @Override
    public SelectStatement createSelect(SelectModel model) throws SqlException {
        try {
            Statement statement = mConnection.createStatement();
            String sql = mSqlBuilder.build(model);
            return new JdbcSelectStatement(statement, sql);
        } catch (SQLException e) {
            throw new SqlException(e);
        }
    }
}
