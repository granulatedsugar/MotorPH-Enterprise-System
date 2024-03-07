package com.mes.motorph.exception;

import java.sql.SQLException;

public class LeaveRequestException extends Exception{
    private SQLException sqlException;

    public  LeaveRequestException(String message) {
        super(message);
    }

    public LeaveRequestException(String message, SQLException sqlException) {
        super(message);
        this.sqlException = sqlException;
    }

    public LeaveRequestException(String message, Exception exception) {
        super(message);
        if (exception instanceof SQLException) {
            this.sqlException = (SQLException) exception;
        }
    }

    public SQLException getSqlException() {
        return sqlException;
    }

}
