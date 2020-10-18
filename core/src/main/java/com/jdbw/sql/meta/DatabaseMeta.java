package com.jdbw.sql.meta;

import com.jdbw.sql.Table;
import com.jdbw.sql.exceptions.SqlException;

import java.util.Set;

public interface DatabaseMeta {

    Table table(String name) throws SqlException;
    Set<Table> allTables() throws SqlException;
}
