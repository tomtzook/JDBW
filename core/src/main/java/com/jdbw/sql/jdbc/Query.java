package com.jdbw.sql.jdbc;

import java.util.List;

public class Query {

    private final String mSql;
    private final List<Object> mParams;

    public Query(String sql, List<Object> params) {
        mSql = sql;
        mParams = params;
    }

    public String getSql() {
        return mSql;
    }

    public List<Object> getParams() {
        return mParams;
    }
}
