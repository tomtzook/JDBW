package com.jdbw.sql.statements.generic;

import com.jdbw.sql.Column;
import com.jdbw.sql.Condition;

import java.util.Collection;
import java.util.Iterator;

public class SqlBuilder {

    public String build(SelectModel model) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT ");
        appendColumnList(stringBuilder, model.getSelect());
        stringBuilder.append("FROM ");
        stringBuilder.append(model.getTable().getName());
        stringBuilder.append("WHERE ");
        appendConditionList(stringBuilder, model.getWhere());

        return stringBuilder.toString();
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

    private void appendConditionList(StringBuilder stringBuilder, Collection<? extends Condition> conditions) {
        for (Iterator<? extends Condition> iterator = conditions.iterator(); iterator.hasNext();) {
            Condition condition = iterator.next();
            // TODO: CONDITION AS SQL
            stringBuilder.append(condition);
            if (iterator.hasNext()) {
                stringBuilder.append(",");
            }
        }
    }
}
