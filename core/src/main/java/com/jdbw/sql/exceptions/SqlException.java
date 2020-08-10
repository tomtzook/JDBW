package com.jdbw.sql.exceptions;

public class SqlException extends Exception {

    public SqlException(String message, Throwable cause) {
        super(message, cause);
    }

    public SqlException(String message) {
        super(message);
    }

    public SqlException(Throwable cause) {
        super(cause);
    }

    public SqlException() {
        super();
    }
}
