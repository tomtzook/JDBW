package com.jdbw.sql;

import com.jdbw.sql.exceptions.SqlException;

public interface ModelLoader {

    <T> T load(ResultRow row, Class<T> type) throws SqlException;
}
