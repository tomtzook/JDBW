package com.jdbw.sql.statements;

import com.jdbw.sql.exceptions.SqlException;

public interface StatementFactory {

    SelectStatement createSelect(SelectModel model) throws SqlException;
    InsertStatement createInsert(InsertModel model) throws SqlException;
}
