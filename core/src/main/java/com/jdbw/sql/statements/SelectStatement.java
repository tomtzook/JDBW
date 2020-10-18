package com.jdbw.sql.statements;

import com.jdbw.sql.Column;
import com.jdbw.sql.conditions.Condition;
import com.jdbw.sql.GroupBy;
import com.jdbw.sql.Limit;
import com.jdbw.sql.OrderBy;
import com.jdbw.sql.QueryResult;
import com.jdbw.sql.exceptions.SqlException;

import java.util.Arrays;
import java.util.Collection;

public interface SelectStatement {

    interface Builder {
        default Builder select(Column... columns) {
            return select(Arrays.asList(columns));
        }
        Builder select(Collection<? extends Column> columns);

        default Builder where(Condition... conditions) {
            return where(Arrays.asList(conditions));
        }
        Builder where(Collection<? extends Condition> conditions);

        Builder orderBy(GroupBy groupBy);
        Builder orderBy(OrderBy orderBy);
        Builder limit(Limit limit);

        SelectStatement build() throws SqlException;
    }

    QueryResult execute() throws SqlException;
}
