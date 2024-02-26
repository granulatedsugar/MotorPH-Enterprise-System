package com.mes.motorph.exception;

import java.sql.SQLException;

public class DepartmentException extends Exception{
    private SQLException sqlException;

    public  DepartmentException(String message) {
        super(message);
    }

    public DepartmentException(String message, SQLException sqlException) {
        super(message);
        this.sqlException = sqlException;
    }

    public DepartmentException(String message, Exception exception) {
        super(message);
        if (exception instanceof SQLException) {
            this.sqlException = (SQLException) exception;
        }
    }

    public SQLException getSqlException() {
        return sqlException;
    }
}
