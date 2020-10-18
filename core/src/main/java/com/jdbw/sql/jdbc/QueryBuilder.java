package com.jdbw.sql.jdbc;

import com.jdbw.sql.Column;
import com.jdbw.sql.conditions.Condition;
import com.jdbw.sql.exceptions.SqlException;
import com.jdbw.sql.jdbc.conditions.JdbcCondition;
import com.jdbw.sql.statements.generic.SelectModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class QueryBuilder {

    public Query build(SelectModel model) throws SqlException {
        StringBuilder stringBuilder = new StringBuilder();
        List<Object> params = new ArrayList<>();

        stringBuilder.append("SELECT ");
        if (model.getSelect().isEmpty()) {
            stringBuilder.append(" * ");
        } else {
            appendColumnList(stringBuilder, model.getSelect());
        }

        stringBuilder.append(" FROM ");
        stringBuilder.append(model.getTable().getName());

        if (!model.getWhere().isEmpty()) {
            stringBuilder.append(" WHERE ");
            appendConditionList(stringBuilder, params, model.getWhere());
        }

        return new Query(stringBuilder.toString(), params);
    }

    private void appendColumnList(StringBuilder stringBuilder, Collection<? extends Column> columns) {
        for (Iterator<? extends Column> iterator = columns.iterator(); iterator.hasNext();) {
            Column column = iterator.next();
            stringBuilder.append(column.getName());
            if (iterator.hasNext()) {
                stringBuilder.append(",");
            }
        }
    }

    private void appendConditionList(StringBuilder stringBuilder, List<Object> params,
                                     Collection<? extends Condition> conditions) throws SqlException {
        for (Iterator<? extends Condition> iterator = conditions.iterator(); iterator.hasNext();) {
            Condition condition = iterator.next();
            if (!(condition instanceof JdbcCondition)) {
                throw new IllegalArgumentException("Expected JdbcCondition");
            }

            ((JdbcCondition) condition).formatSql(stringBuilder, params);
            if (iterator.hasNext()) {
                stringBuilder.append(" AND ");
            }
        }
    }
}
