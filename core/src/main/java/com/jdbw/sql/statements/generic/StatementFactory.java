package com.jdbw.sql.statements.generic;

import com.jdbw.sql.exceptions.SqlException;
import com.jdbw.sql.statements.SelectStatement;

public interface StatementFactory {

    SelectStatement createSelect(SelectModel model) throws SqlException;
}
