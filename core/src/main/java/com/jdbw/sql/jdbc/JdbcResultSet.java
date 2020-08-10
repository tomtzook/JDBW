package com.jdbw.sql.jdbc;

import com.jdbw.sql.QueryResult;
import com.jdbw.sql.ResultRow;
import com.jdbw.sql.exceptions.SqlException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcResultSet implements QueryResult {

    private final ResultSet mResultSet;

    public JdbcResultSet(ResultSet resultSet) {
        mResultSet = resultSet;
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
    public ResultRow get() {
        return null;
    }

    @Override
    public void close() {

    }
}
