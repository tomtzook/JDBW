package com.jdbw.sql.jdbc.statements;

import com.castle.util.throwables.ThrowableChain;
import com.castle.util.throwables.Throwables;
import com.jdbw.sql.QueryResult;
import com.jdbw.sql.exceptions.SqlException;
import com.jdbw.sql.jdbc.JdbcResultSet;
import com.jdbw.sql.statements.SelectStatement;

import java.sql.SQLException;
import java.sql.Statement;

public class JdbcSelectStatement implements SelectStatement {

    private final Statement mStatement;
    private final String mSql;

    public JdbcSelectStatement(Statement statement, String sql) {
        mStatement = statement;
        mSql = sql;
    }

    @Override
    public QueryResult execute() throws SqlException {
        ThrowableChain chain = Throwables.newChain();

        try {
            return new JdbcResultSet(mStatement.executeQuery(mSql));
        } catch (SQLException e) {
            chain.chain(e);
        } finally {
            try {
                mStatement.close();
            } catch (SQLException e) {
                chain.chain(e);
            }
        }

        chain.throwAsType(SqlException.class, SqlException::new);
        // won't be reached
        throw new AssertionError();
    }
}
