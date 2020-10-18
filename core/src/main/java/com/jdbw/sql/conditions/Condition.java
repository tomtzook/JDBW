package com.jdbw.sql.conditions;

public interface Condition {

    Condition and(Condition other);
    Condition or(Condition other);
}
