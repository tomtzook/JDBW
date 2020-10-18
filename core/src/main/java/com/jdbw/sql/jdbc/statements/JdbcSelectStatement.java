package com.jdbw.sql.jdbc.statements;

import com.castle.util.throwables.ThrowableChain;
import com.castle.util.throwables.Throwables;
import com.jdbw.sql.QueryResult;
import com.jdbw.sql.exceptions.SqlException;
import com.jdbw.sql.jdbc.JdbcResultSet;
import com.jdbw.sql.jdbc.ResultRowParser;
import com.jdbw.sql.statements.SelectStatement;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcSelectStatement implements SelectStatement {

    private final PreparedStatement mStatement;
    private final ResultRowParser mResultRowParser;

    public JdbcSelectStatement(PreparedStatement statement, ResultRowParser resultRowParser) {
        mStatement = statement;
        mResultRowParser = resultRowParser;
    }

    @Override
    public QueryResult execute() throws SqlException {
        ThrowableChain chain = Throwables.newChain();

        try {
            return new JdbcResultSet(mStatement, mStatement.executeQuery(), mResultRowParser);
        } catch (SQLException e) {
            chain.chain(e);

            try {
                mStatement.close();
            } catch (SQLException e1) {
                chain.chain(e1);
            }
        }

        chain.throwAsType(SqlException.class, SqlException::new);
        // won't be reached
        throw new AssertionError();
    }
}
