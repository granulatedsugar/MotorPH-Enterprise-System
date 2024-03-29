package com.mes.motorph.exception;

import java.sql.SQLException;

public class PositionException extends Exception {
    private SQLException sqlException;

    public  PositionException(String message) {
        super(message);
    }

    public PositionException(String message, SQLException sqlException) {
        super(message);
        this.sqlException = sqlException;
    }

    public PositionException(String message, Exception exception) {
        super(message);
        if (exception instanceof SQLException) {
            this.sqlException = (SQLException) exception;
        }
    }

    public SQLException getSqlException() {
        return sqlException;
    }
}
