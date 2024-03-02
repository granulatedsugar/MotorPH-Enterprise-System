package com.mes.motorph.exception;

import java.sql.SQLException;

public class UserRoleException extends Exception{
    private SQLException sqlException;

    public UserRoleException(String messsage) {
        super(messsage);
    }

    public UserRoleException(String message, SQLException sqlException) {
        super(message);
        this.sqlException = sqlException;
    }

    public UserRoleException(String message, Exception e) {
        super(message);

        if (e instanceof SQLException) {
            this.sqlException = (SQLException) e;
        }
    }

    public SQLException getSqlException() {
        return sqlException;
    }
}
