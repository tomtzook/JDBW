package com.jdbw.sql.conditions;

import com.jdbw.sql.Column;
import com.jdbw.sql.ColumnValue;

import java.util.Arrays;
import java.util.Collection;

public interface ConditionFactory {

    default Condition equals(Column column, Object value) {
        return equals(column, ColumnValue.ofNullable(value));
    }
    Condition equals(Column column, ColumnValue value);

    default Condition notEqual(Column column, Object value) {
        return notEqual(column, ColumnValue.ofNullable(value));
    }
    Condition notEqual(Column column, ColumnValue value);

    default Condition or(Condition... conditions) {
        return or(Arrays.asList(conditions));
    }
    Condition or(Collection<Condition> conditions);

    default Condition and(Condition... conditions) {
        return and(Arrays.asList(conditions));
    }
    Condition and(Collection<Condition> conditions);
}
