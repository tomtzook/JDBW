package com.jdbw.sql.jdbc.conditions;

import com.jdbw.sql.Column;
import com.jdbw.sql.ColumnValue;
import com.jdbw.sql.conditions.Condition;
import com.jdbw.sql.conditions.ConditionFactory;

import java.util.Collection;

public class JdbcConditionFactory implements ConditionFactory {

    @Override
    public Condition equals(Column column, ColumnValue value) {
        return new EqualCondition(column, value);
    }

    @Override
    public Condition notEqual(Column column, ColumnValue value) {
        return new NotEqualCondition(column, value);
    }

    @Override
    public Condition or(Collection<Condition> conditions) {
        return new LogicalCombinedCondition(conditions, " OR ");
    }

    @Override
    public Condition and(Collection<Condition> conditions) {
        return new LogicalCombinedCondition(conditions, " AND ");
    }
}
