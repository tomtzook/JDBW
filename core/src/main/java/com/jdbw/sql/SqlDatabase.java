package com.jdbw.sql;

import com.jdbw.Database;
import com.jdbw.sql.conditions.ConditionFactory;
import com.jdbw.sql.exceptions.SqlException;
import com.jdbw.sql.statements.SelectStatement;

public interface SqlDatabase extends Database {

    Table table(String name) throws SqlException;

    ConditionFactory conditions();

    SelectStatement.Builder select(Table table);
}
