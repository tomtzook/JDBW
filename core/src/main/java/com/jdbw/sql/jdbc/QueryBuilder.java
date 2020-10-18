package com.jdbw.sql.jdbc;

import com.jdbw.sql.Column;
import com.jdbw.sql.ColumnValue;
import com.jdbw.sql.conditions.Condition;
import com.jdbw.sql.exceptions.SqlException;
import com.jdbw.sql.jdbc.conditions.JdbcCondition;
import com.jdbw.sql.statements.InsertModel;
import com.jdbw.sql.statements.SelectModel;

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

    public Query build(InsertModel model) throws SqlException {
        StringBuilder stringBuilder = new StringBuilder();
        List<Object> params = new ArrayList<>();

        stringBuilder.append("INSERT INTO ")
                .append(model.getTable().getName())
                .append(' ');
        if (!model.getColumns().isEmpty()) {
            stringBuilder.append('(');
            appendColumnList(stringBuilder, model.getColumns());
            stringBuilder.append(") ");
        }

        stringBuilder.append("VALUES ");
        for (Iterator<Collection<? extends ColumnValue>> iterator = model.getValues().iterator();
             iterator.hasNext();) {
            Collection<? extends ColumnValue> values = iterator.next();
            stringBuilder.append('(');
            appendValuesList(stringBuilder, params, values);
            stringBuilder.append(')');

            if (iterator.hasNext()) {
                stringBuilder.append(',');
            }
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

    private void appendValuesList(StringBuilder stringBuilder, List<Object> params,
                                  Collection<? extends ColumnValue> values) {
        for (Iterator<? extends ColumnValue> iterator = values.iterator(); iterator.hasNext();) {
            ColumnValue value = iterator.next();
            stringBuilder.append('?');
            params.add(value.isNull() ? null : value.getRawValue());

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
