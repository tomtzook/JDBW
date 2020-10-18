package com.jdbw.sql.jdbc;

import com.jdbw.sql.Column;
import com.jdbw.sql.ColumnFlag;
import com.jdbw.sql.ColumnType;
import com.jdbw.sql.Table;
import com.jdbw.sql.conditions.ConditionFactory;
import com.jdbw.sql.exceptions.SqlException;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

public class DatabaseMeta {

    private final Connection mConnection;
    private final ConditionFactory mConditionFactory;

    public DatabaseMeta(Connection connection, ConditionFactory conditionFactory) {
        mConnection = connection;
        mConditionFactory = conditionFactory;
    }

    public Table getTable(String name) throws SqlException {
        try {
            DatabaseMetaData metaData = mConnection.getMetaData();
            try (ResultSet resultSet = metaData.getTables(null, null, "%s", new String[]{"TABLE"})) {
                while (resultSet.next()) {
                    String tableName = resultSet.getString("TABLE_NAME");
                    if (tableName.equals(name)) {
                        return new JdbcTable(tableName, this);
                    }
                }
            }

            throw new SqlException("Table not found: " + name);
        } catch (SQLException e) {
            throw new SqlException(e);
        }
    }

    public Column getColumn(String tableName, String columnName) throws SqlException {
        try {
            DatabaseMetaData metaData = mConnection.getMetaData();
            try (ResultSet resultSet = metaData.getColumns(null, null, tableName, null)) {
                while (resultSet.next()) {
                    String name = resultSet.getString("COLUMN_NAME");
                    int dataType = resultSet.getInt("DATA_TYPE");

                    if (columnName.equals(name)) {
                        ColumnType type = parseType(dataType);
                        Collection<ColumnFlag> flags = parseFlags(resultSet);
                        return new JdbcColumn(name, type, mConditionFactory, flags);
                    }
                }
            }

            throw new SqlException("Column not found: " + columnName);
        } catch (SQLException e) {
            throw new SqlException(e);
        }
    }

    private ColumnType parseType(int dataType) throws SqlException {
        // https://www.cis.upenn.edu/~bcpierce/courses/629/jdkdocs/guide/jdbc/getstart/mapping.doc.html
        switch (dataType) {
            case Types.CHAR:
                return ColumnType.CHAR;
            case Types.VARCHAR:
            case Types.LONGVARCHAR:
            case Types.LONGNVARCHAR:
            case Types.NVARCHAR:
                return ColumnType.TEXT;
            case Types.BIT:
                return ColumnType.BOOLEAN;
            case Types.INTEGER:
            case Types.BIGINT:
            case Types.SMALLINT:
            case Types.TINYINT:
                return ColumnType.INTEGER;
            case Types.REAL:
            case Types.DOUBLE:
            case Types.FLOAT:
                return ColumnType.REAL;
            case Types.BLOB:
            case Types.BINARY:
            case Types.LONGVARBINARY:
            case Types.VARBINARY:
                return ColumnType.BLOB;
            case Types.DATE:
                return ColumnType.DATE;
            case Types.TIME:
                return ColumnType.TIME;
            case Types.TIMESTAMP:
                return ColumnType.TIMESTAMP;
            default:
                throw new SqlException("Unknown type: " + dataType);
        }
    }

    private Collection<ColumnFlag> parseFlags(ResultSet resultSet) throws SQLException {
        String isNullable = resultSet.getString("IS_NULLABLE");
        String isAutoIncrement = resultSet.getString("IS_AUTOINCREMENT");

        Collection<ColumnFlag> columnFlags = new ArrayList<>();

        if (isNullable.equalsIgnoreCase("NO")) {
            columnFlags.add(ColumnFlag.NOT_NULL);
        }
        if (isAutoIncrement.equalsIgnoreCase("YES")) {
            columnFlags.add(ColumnFlag.AUTO_INCREMENT);
        }

        return columnFlags;
    }
}
