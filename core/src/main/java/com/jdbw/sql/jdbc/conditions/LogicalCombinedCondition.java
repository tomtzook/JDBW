package com.jdbw.sql.jdbc.conditions;

import com.jdbw.sql.conditions.Condition;
import com.jdbw.sql.exceptions.SqlException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class LogicalCombinedCondition implements JdbcCondition {

    private final Collection<Condition> mConditions;
    private final String mDelimiter;

    public LogicalCombinedCondition(Collection<Condition> conditions, String delimiter) {
        mConditions = conditions;
        mDelimiter = delimiter;
    }

    @Override
    public void formatSql(StringBuilder stringBuilder, List<Object> params) throws SqlException {
        if (mConditions.isEmpty()) {
            return;
        }

        stringBuilder.append('(');
        for (Iterator<Condition> iterator = mConditions.iterator(); iterator.hasNext();) {
            Condition condition = iterator.next();
            if (!(condition instanceof JdbcCondition)) {
                throw new SqlException("Expected JdbcCondition");
            }

            ((JdbcCondition) condition).formatSql(stringBuilder, params);
            if (iterator.hasNext()) {
                stringBuilder.append(mDelimiter);
            }
        }
        stringBuilder.append(')');
    }

    @Override
    public Condition and(Condition other) {
        if (mDelimiter.equals(" AND ")) {
            mConditions.add(other);
            return this;
        }

        List<Condition> copy = new ArrayList<>(mConditions);
        copy.add(other);
        return new LogicalCombinedCondition(copy, " AND ");
    }

    @Override
    public Condition or(Condition other) {
        if (mDelimiter.equals(" OR ")) {
            mConditions.add(other);
            return this;
        }

        List<Condition> copy = new ArrayList<>(mConditions);
        copy.add(other);
        return new LogicalCombinedCondition(copy, " OR ");
    }
}
