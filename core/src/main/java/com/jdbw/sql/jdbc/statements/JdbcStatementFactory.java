package com.jdbw.sql.jdbc.statements;

import com.jdbw.sql.ModelLoader;
import com.jdbw.sql.exceptions.SqlException;
import com.jdbw.sql.jdbc.Query;
import com.jdbw.sql.jdbc.QueryBuilder;
import com.jdbw.sql.jdbc.ResultRowParser;
import com.jdbw.sql.jdbc.SqlObjectAdapter;
import com.jdbw.sql.statements.InsertModel;
import com.jdbw.sql.statements.InsertStatement;
import com.jdbw.sql.statements.SelectModel;
import com.jdbw.sql.statements.SelectStatement;
import com.jdbw.sql.statements.StatementFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class JdbcStatementFactory implements StatementFactory {

    private final Connection mConnection;
    private final QueryBuilder mQueryBuilder;
    private final SqlObjectAdapter mObjectAdapter;
    private final ResultRowParser mResultRowParser;
    private final ModelLoader mModelLoader;

    public JdbcStatementFactory(Connection connection, QueryBuilder queryBuilder, SqlObjectAdapter objectAdapter,
                                ResultRowParser resultRowParser, ModelLoader modelLoader) {
        mConnection = connection;
        mQueryBuilder = queryBuilder;
        mObjectAdapter = objectAdapter;
        mResultRowParser = resultRowParser;
        mModelLoader = modelLoader;
    }

    @Override
    public SelectStatement createSelect(SelectModel model) throws SqlException {
        try {
            Query query = mQueryBuilder.build(model);
            PreparedStatement statement = mConnection.prepareStatement(query.getSql());
            fillParameters(statement, query.getParams());

            return new JdbcSelectStatement(statement, mResultRowParser, mModelLoader);
        } catch (SQLException e) {
            throw new SqlException(e);
        }
    }

    @Override
    public InsertStatement createInsert(InsertModel model) throws SqlException {
        try {
            Query query = mQueryBuilder.build(model);
            PreparedStatement statement = mConnection.prepareStatement(query.getSql());
            fillParameters(statement, query.getParams());

            return new JdbcInsertStatement(statement);
        } catch (SQLException e) {
            throw new SqlException(e);
        }
    }

    private void fillParameters(PreparedStatement statement, List<Object> params) throws SqlException {
        for (int i = 1; i <= params.size(); i++) {
            mObjectAdapter.putInStatement(statement, i, params.get(i-1));
        }
    }
}
