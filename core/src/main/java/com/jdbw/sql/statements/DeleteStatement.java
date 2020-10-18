package com.jdbw.sql.statements;

import com.jdbw.sql.conditions.Condition;
import com.jdbw.sql.exceptions.SqlException;

import java.util.Arrays;
import java.util.Collection;

public interface DeleteStatement {

    interface Builder {
        default Builder where(Condition... conditions) {
            return where(Arrays.asList(conditions));
        }
        Builder where(Collection<? extends Condition> conditions);

        DeleteStatement build() throws SqlException;
    }

    void execute() throws SqlException;
}
