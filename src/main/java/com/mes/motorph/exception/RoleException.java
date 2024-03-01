package com.mes.motorph.exception;

import java.sql.SQLException;

public class RoleException extends Exception{
    private SQLException sqlException;
    public RoleException(String message) {
        super(message);
    }

    public RoleException(String message, SQLException sqlException) {
        super(message);
        this.sqlException = sqlException;
    }

    public RoleException(String message, Exception e) {
        super(message);

        if (e instanceof SQLException) {
            this.sqlException = (SQLException) e;
        }
    }

    public SQLException getSqlException() {
        return sqlException;
    }
}
