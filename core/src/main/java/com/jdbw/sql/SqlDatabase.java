package com.jdbw.sql;

import com.jdbw.Database;
import com.jdbw.sql.conditions.ConditionFactory;
import com.jdbw.sql.statements.SelectStatement;

public interface SqlDatabase extends Database {

    ConditionFactory conditions();

    SelectStatement.Builder select(Table table);
}
