package com.jdbw.sql;

import com.jdbw.sql.exceptions.SqlException;

import java.io.Closeable;

public interface QueryResult extends Closeable {

    boolean next() throws SqlException;
    ResultRow get() throws SqlException;

    @Override
    void close();
}
