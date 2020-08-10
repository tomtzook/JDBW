package com.jdbw.sql;

import com.jdbw.Database;
import com.jdbw.sql.statements.SelectStatement;

public interface SqlDatabase extends Database {

    SelectStatement.Builder select(Table table);
}
