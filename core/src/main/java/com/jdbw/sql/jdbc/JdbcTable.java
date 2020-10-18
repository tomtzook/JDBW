package com.jdbw.sql.jdbc;

import com.jdbw.sql.Column;
import com.jdbw.sql.Table;
import com.jdbw.sql.exceptions.SqlException;
import com.jdbw.sql.jdbc.meta.JdbcDatabaseMeta;

public class JdbcTable implements Table {

    private final String mName;
    private final JdbcDatabaseMeta mMeta;

    public JdbcTable(String name, JdbcDatabaseMeta meta) {
        mName = name;
        mMeta = meta;
    }

    public String getName() {
        return mName;
    }

    @Override
    public Column column(String name) throws SqlException {
        return mMeta.getColumn(mName, name);
    }

    @Override
    public String toString() {
        return String.format("Table{%s}", mName);
    }
}
