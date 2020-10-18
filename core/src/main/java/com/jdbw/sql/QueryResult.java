package com.jdbw.sql;

import com.castle.util.function.ThrowingFunction;
import com.jdbw.sql.exceptions.SqlException;
import com.jdbw.util.ThrowingConsumer;

import java.io.Closeable;
import java.util.List;

public interface QueryResult extends Closeable {

    boolean next() throws SqlException;
    ResultRow get() throws SqlException;

    <T> T getAs(Class<T> type) throws SqlException;
    <T> T getAs(ThrowingFunction<ResultRow, T, SqlException> rowFactory) throws SqlException;

    @Override
    void close();

    void forEach(ThrowingConsumer<ResultRow, SqlException> consumer) throws SqlException;
    <T> void forEach(ThrowingConsumer<T, SqlException> consumer, Class<T> type) throws SqlException;
    <T> void forEach(ThrowingConsumer<T, SqlException> consumer,
                     ThrowingFunction<ResultRow, T, SqlException> rowFactory) throws SqlException;

    List<ResultRow> collectAll() throws SqlException;
    <T> List<T> collectAll(Class<T> type) throws SqlException;
    <T> List<T> collectAll(ThrowingFunction<ResultRow, T, SqlException> rowFactory) throws SqlException;
}
