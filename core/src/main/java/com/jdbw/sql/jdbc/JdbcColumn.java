package com.jdbw.sql.jdbc;

import com.jdbw.sql.Column;
import com.jdbw.sql.ColumnFlag;
import com.jdbw.sql.ColumnType;
import com.jdbw.sql.ColumnValue;
import com.jdbw.sql.conditions.Condition;
import com.jdbw.sql.conditions.ConditionFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class JdbcColumn implements Column {

    private final String mName;
    private final ColumnType mType;
    private final Collection<ColumnFlag> mFlags;

    private final ConditionFactory mConditionFactory;

    public JdbcColumn(String name, ColumnType type, ConditionFactory conditionFactory, Collection<ColumnFlag> flags) {
        mName = name;
        mType = type;
        mFlags = flags;

        mConditionFactory = conditionFactory;
    }

    public JdbcColumn(String name, ColumnType type, ConditionFactory conditionFactory, ColumnFlag... flags) {
        this(name, type, conditionFactory, Arrays.asList(flags));
    }

    public String getName() {
        return mName;
    }

    public ColumnType getType() {
        return mType;
    }

    public Collection<ColumnFlag> getFlags() {
        return Collections.unmodifiableCollection(mFlags);
    }

    @Override
    public Condition equal(ColumnValue value) {
        return mConditionFactory.equals(this, value);
    }

    @Override
    public Condition notEqual(ColumnValue value) {
        return mConditionFactory.notEqual(this, value);
    }

    @Override
    public String toString() {
        return String.format("Column{name=%s,type=%s,flags=%s}", mName, mType, mFlags);
    }
}
