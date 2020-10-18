package com.jdbw.sql.jdbc;

import com.jdbw.sql.ColumnValue;
import com.jdbw.sql.ResultRow;
import com.jdbw.sql.exceptions.SqlException;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public class ResultRowParser {

    public ResultRow parseRowFromSet(ResultSet set) throws SqlException {
        Map<String, ColumnValue> values = new HashMap<>();
        try {
            ResultSetMetaData metaData = set.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String name = metaData.getColumnName(i);
                int type = metaData.getColumnType(i);
                Object value = set.getObject(i);

                values.put(name, parseValue(type, value));
            }

            return new ResultRow(values);
        } catch (SQLException e) {
            throw new SqlException(e);
        }
    }

    private ColumnValue parseValue(int type, Object value) {
        if (type == Types.NULL) {
            return ColumnValue.nullValue();
        }
        return ColumnValue.of(value);
    }
}
