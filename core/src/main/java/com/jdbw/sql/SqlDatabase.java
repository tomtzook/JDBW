package com.jdbw.sql;

import com.jdbw.Database;
import com.jdbw.sql.conditions.ConditionFactory;
import com.jdbw.sql.exceptions.SqlException;
import com.jdbw.sql.meta.DatabaseMeta;
import com.jdbw.sql.statements.DeleteStatement;
import com.jdbw.sql.statements.InsertStatement;
import com.jdbw.sql.statements.SelectStatement;
import com.jdbw.sql.statements.UpdateStatement;

public interface SqlDatabase extends Database {

    DatabaseMeta meta() throws SqlException;

    ConditionFactory conditions();

    SelectStatement.Builder select(Table table);
    InsertStatement.Builder insert(Table table);
    UpdateStatement.Builder update(Table table);
    DeleteStatement.Builder delete(Table table);
}
