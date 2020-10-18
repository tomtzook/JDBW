package com.jdbw.sql.jdbc;

import com.jdbw.sql.QueryResult;
import com.jdbw.sql.ResultRow;
import com.jdbw.sql.exceptions.SqlException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcResultSet implements QueryResult {

    private final Statement mStatement;
    private final ResultSet mResultSet;
    private final ResultRowParser mResultRowParser;

    public JdbcResultSet(Statement statement, ResultSet resultSet, ResultRowParser resultRowParser) {
        mStatement = statement;
        mResultSet = resultSet;
        mResultRowParser = resultRowParser;
    }

    @Override
    public boolean next() throws SqlException {
        try {
            return mResultSet.next();
        } catch (SQLException e) {
            throw new SqlException(e);
        }
    }

    @Override
    public ResultRow get() throws SqlException {
        return mResultRowParser.parseRowFromSet(mResultSet);
    }

    @Override
    public void close() {
        try {
            mStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
