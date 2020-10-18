package com.jdbw.sql.jdbc;

import com.jdbw.sql.Column;
import com.jdbw.sql.ColumnValue;
import com.jdbw.sql.Order;
import com.jdbw.sql.conditions.Condition;
import com.jdbw.sql.exceptions.SqlException;
import com.jdbw.sql.jdbc.conditions.JdbcCondition;
import com.jdbw.sql.statements.DeleteModel;
import com.jdbw.sql.statements.InsertModel;
import com.jdbw.sql.statements.SelectModel;
import com.jdbw.sql.statements.UpdateModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

        if (!model.getGroup().isEmpty()) {
            stringBuilder.append(" GROUP BY ");
            appendColumnList(stringBuilder, model.getGroup());
        }

        if (!model.getOrder().isEmpty()) {
            stringBuilder.append(" ORDER BY ");
            appendOrderList(stringBuilder, model.getOrder());
        }

        if (model.getLimit() != null) {
            // TODO: ONLY FOR MYSQL
            // https://www.w3schools.com/sql/sql_top.asp
            stringBuilder.append(" LIMIT ");
            stringBuilder.append(model.getLimit().getAmount());
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

    public Query build(UpdateModel model) throws SqlException {
        StringBuilder stringBuilder = new StringBuilder();
        List<Object> params = new ArrayList<>();

        stringBuilder.append("UPDATE ")
                .append(model.getTable().getName());

        if (!model.getValues().isEmpty()) {
            stringBuilder.append(" SET ");
            appendValuesMap(stringBuilder, params, model.getValues());
        }

        if (!model.getWhere().isEmpty()) {
            stringBuilder.append(" WHERE ");
            appendConditionList(stringBuilder, params, model.getWhere());
        }

        return new Query(stringBuilder.toString(), params);
    }

    public Query build(DeleteModel model) throws SqlException {
        StringBuilder stringBuilder = new StringBuilder();
        List<Object> params = new ArrayList<>();

        stringBuilder.append("DELETE FROM ")
                .append(model.getTable().getName());

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

    private void appendValuesMap(StringBuilder stringBuilder, List<Object> params,
                                  Map<Column, ColumnValue> values) {
        for (Iterator<Map.Entry<Column, ColumnValue>> iterator = values.entrySet().iterator();
             iterator.hasNext();) {
            Map.Entry<Column, ColumnValue> entry = iterator.next();
            stringBuilder.append(entry.getKey().getName());
            stringBuilder.append("=?");
            params.add(entry.getValue().isNull() ? null : entry.getValue().getRawValue());

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

    private void appendOrderList(StringBuilder stringBuilder, Map<Column, Order> order) throws SqlException {
        for (Iterator<Map.Entry<Column, Order>> iterator = order.entrySet().iterator();
             iterator.hasNext();) {
            Map.Entry<Column, Order> entry = iterator.next();
            stringBuilder.append(entry.getKey().getName());
            stringBuilder.append(' ');
            stringBuilder.append(entry.getValue().name());

            if (iterator.hasNext()) {
                stringBuilder.append(",");
            }
        }
    }
}
