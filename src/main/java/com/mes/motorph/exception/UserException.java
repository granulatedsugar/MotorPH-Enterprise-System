package com.mes.motorph.exception;

import java.sql.SQLException;

public class UserException extends Exception {
    private SQLException sqlException;

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, SQLException sqlException) {
        super(message);
        this.sqlException = sqlException;
    }

    public UserException(String message, Exception exception) {
        super(message);
        if(exception instanceof SQLException) {
            this.sqlException = (SQLException) exception;
        }
    }

}
