package com.jdbw.sql;

import com.jdbw.Database;
import com.jdbw.sql.conditions.ConditionFactory;
import com.jdbw.sql.exceptions.SqlException;
import com.jdbw.sql.meta.DatabaseMeta;
import com.jdbw.sql.statements.InsertStatement;
import com.jdbw.sql.statements.SelectStatement;

public interface SqlDatabase extends Database {

    DatabaseMeta meta() throws SqlException;

    ConditionFactory conditions();

    SelectStatement.Builder select(Table table);
    InsertStatement.Builder insert(Table table);
}
