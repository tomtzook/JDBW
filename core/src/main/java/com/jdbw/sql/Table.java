package com.jdbw.sql;

import com.jdbw.sql.exceptions.SqlException;

public interface Table {

    String getName();

    Column column(String name) throws SqlException;
}
