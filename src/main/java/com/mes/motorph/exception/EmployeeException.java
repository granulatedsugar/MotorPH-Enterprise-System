package com.mes.motorph.exception;

import java.sql.SQLException;

public class EmployeeException extends Exception{

    private SQLException sqlException;

    public EmployeeException(String message, SQLException sqlException) {
        super(message);
        this.sqlException = sqlException;
    }

    public EmployeeException(String message, Exception exception) {
        super(message);
        if (exception instanceof SQLException) {
            this.sqlException = (SQLException) exception;
        }
    }

    public SQLException getSqlException() {
        return sqlException;
    }
}
