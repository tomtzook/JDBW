package com.jdbw.sql.jdbc.conditions;

import com.jdbw.sql.Column;
import com.jdbw.sql.ColumnValue;

import java.util.List;

public class EqualCondition implements JdbcCondition {

    private final Column mColumn;
    private final ColumnValue mValue;

    public EqualCondition(Column column, ColumnValue value) {
        mColumn = column;
        mValue = value;
    }

    @Override
    public void formatSql(StringBuilder stringBuilder, List<Object> params) {
        stringBuilder.append(mColumn.getName());
        stringBuilder.append("=?");
        params.add(mValue.getRawValue());
    }
}
