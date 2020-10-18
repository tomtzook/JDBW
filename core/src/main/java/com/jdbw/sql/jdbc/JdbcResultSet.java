package com.jdbw.sql.jdbc;

import com.castle.util.function.ThrowingFunction;
import com.jdbw.sql.ModelLoader;
import com.jdbw.sql.QueryResult;
import com.jdbw.sql.ResultRow;
import com.jdbw.sql.exceptions.SqlException;
import com.jdbw.util.ThrowingConsumer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcResultSet implements QueryResult {

    private final Statement mStatement;
    private final ResultSet mResultSet;
    private final ResultRowParser mResultRowParser;
    private final ModelLoader mModelLoader;

    public JdbcResultSet(Statement statement, ResultSet resultSet, ResultRowParser resultRowParser, ModelLoader modelLoader) {
        mStatement = statement;
        mResultSet = resultSet;
        mResultRowParser = resultRowParser;
        mModelLoader = modelLoader;
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
    public <T> T getAs(Class<T> type) throws SqlException {
        return getAs((row) -> mModelLoader.load(row, type));
    }

    @Override
    public <T> T getAs(ThrowingFunction<ResultRow, T, SqlException> rowFactory) throws SqlException {
        return rowFactory.apply(get());
    }

    @Override
    public void forEach(ThrowingConsumer<ResultRow, SqlException> consumer) throws SqlException {
        while (next()) {
            consumer.accept(get());
        }
    }

    @Override
    public <T> void forEach(ThrowingConsumer<T, SqlException> consumer, Class<T> type) throws SqlException {
        forEach(consumer, (row) -> mModelLoader.load(row, type));
    }

    @Override
    public <T> void forEach(ThrowingConsumer<T, SqlException> consumer, ThrowingFunction<ResultRow, T, SqlException> rowFactory) throws SqlException {
        while (next()) {
            consumer.accept(getAs(rowFactory));
        }
    }

    @Override
    public List<ResultRow> collectAll() throws SqlException {
        List<ResultRow> rows = new ArrayList<>();
        while (next()) {
            rows.add(get());
        }

        return rows;
    }

    @Override
    public <T> List<T> collectAll(Class<T> type) throws SqlException {
        return collectAll((row) -> mModelLoader.load(row, type));
    }

    @Override
    public <T> List<T> collectAll(ThrowingFunction<ResultRow, T, SqlException> rowFactory) throws SqlException {
        List<T> rows = new ArrayList<>();
        while (next()) {
            rows.add(getAs(rowFactory));
        }

        return rows;
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
