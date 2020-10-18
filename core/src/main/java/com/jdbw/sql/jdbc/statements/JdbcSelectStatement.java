package com.jdbw.sql.jdbc.statements;

import com.castle.util.throwables.ThrowableChain;
import com.castle.util.throwables.Throwables;
import com.jdbw.sql.ModelLoader;
import com.jdbw.sql.QueryResult;
import com.jdbw.sql.ResultRow;
import com.jdbw.sql.exceptions.SqlException;
import com.jdbw.sql.jdbc.JdbcResultSet;
import com.jdbw.sql.jdbc.ResultRowParser;
import com.jdbw.sql.statements.SelectStatement;
import com.jdbw.util.ThrowingConsumer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class JdbcSelectStatement implements SelectStatement {

    private final PreparedStatement mStatement;
    private final ResultRowParser mResultRowParser;
    private final ModelLoader mModelLoader;

    public JdbcSelectStatement(PreparedStatement statement, ResultRowParser resultRowParser, ModelLoader modelLoader) {
        mStatement = statement;
        mResultRowParser = resultRowParser;
        mModelLoader = modelLoader;
    }

    @Override
    public QueryResult execute() throws SqlException {
        ThrowableChain chain = Throwables.newChain();

        try {
            return new JdbcResultSet(mStatement, mStatement.executeQuery(), mResultRowParser, mModelLoader);
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

    @Override
    public void execute(ThrowingConsumer<QueryResult, SqlException> consumer) throws SqlException {
        try (QueryResult queryResult = execute()) {
            consumer.accept(queryResult);
        }
    }

    @Override
    public void executeForEach(ThrowingConsumer<ResultRow, SqlException> consumer) throws SqlException {
        try (QueryResult queryResult = execute()) {
            queryResult.forEach(consumer);
        }
    }

    @Override
    public <T> void executeForEach(ThrowingConsumer<T, SqlException> consumer, Class<T> type) throws SqlException {
        try (QueryResult queryResult = execute()) {
            queryResult.forEach(consumer, type);
        }
    }

    @Override
    public List<ResultRow> executeAndCollect() throws SqlException {
        try (QueryResult queryResult = execute()) {
            return queryResult.collectAll();
        }
    }

    @Override
    public <T> List<T> executeAndCollect(Class<T> type) throws SqlException {
        try (QueryResult queryResult = execute()) {
            return queryResult.collectAll(type);
        }
    }
}
