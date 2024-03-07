package com.mes.motorph.exception;

import java.sql.SQLException;

public class OvertimeException extends Exception{
    private SQLException sqlException;

    public  OvertimeException(String message) {
        super(message);
    }

    public OvertimeException(String message, SQLException sqlException) {
        super(message);
        this.sqlException = sqlException;
    }

    public OvertimeException(String message, Exception exception) {
        super(message);
        if (exception instanceof SQLException) {
            this.sqlException = (SQLException) exception;
        }
    }

    public SQLException getSqlException() {
        return sqlException;
    }
}
