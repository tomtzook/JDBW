package com.jdbw.sql;

import java.util.Objects;

public class ColumnValue {

    private static ColumnValue NULL = new ColumnValue(null);

    private final Object mRawValue;

    private ColumnValue(Object rawValue) {
        mRawValue = rawValue;
    }

    public static ColumnValue of(Object value) {
        Objects.requireNonNull(value);
        return new ColumnValue(value);
    }

    public static ColumnValue nullValue() {
        return NULL;
    }

    public static ColumnValue ofNullable(Object value) {
        return value != null ? of(value) : nullValue();
    }

    public boolean isNull() {
        return mRawValue == null;
    }

    public Object getRawValue() {
        if (isNull()) {
            throw new NullPointerException("null value");
        }

        return mRawValue;
    }

    @Override
    public String toString() {
        if (isNull()) {
            return "NULL";
        }

        return String.format("%s{%s}", mRawValue.getClass().getSimpleName(), mRawValue);
    }
}
