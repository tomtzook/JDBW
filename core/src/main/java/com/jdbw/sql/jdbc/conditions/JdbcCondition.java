package com.jdbw.sql.jdbc.conditions;

import com.jdbw.sql.conditions.Condition;
import com.jdbw.sql.exceptions.SqlException;

import java.util.Arrays;
import java.util.List;

public interface JdbcCondition extends Condition {

    void formatSql(StringBuilder stringBuilder, List<Object> params) throws SqlException;

    @Override
    default Condition and(Condition other) {
        return new LogicalCombinedCondition(Arrays.asList(this, other), " AND ");
    }

    @Override
    default Condition or(Condition other) {
        return new LogicalCombinedCondition(Arrays.asList(this, other), " OR ");
    }
}
