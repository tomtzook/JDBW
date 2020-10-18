package com.jdbw.sql;

import com.jdbw.sql.conditions.Condition;

import java.util.Collection;

public interface Column {

    String getName();
    ColumnType getType();
    Collection<ColumnFlag> getFlags();

    default Condition equal(Object value) {
        return equal(ColumnValue.ofNullable(value));
    }
    Condition equal(ColumnValue value);

    default Condition notEqual(Object value) {
        return notEqual(ColumnValue.ofNullable(value));
    }
    Condition notEqual(ColumnValue value);
}
