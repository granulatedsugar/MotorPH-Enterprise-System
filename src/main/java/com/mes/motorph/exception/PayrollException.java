package com.mes.motorph.exception;

public class PayrollException extends Exception {

    public PayrollException(String message) {
        super(message);
    }

    public PayrollException(String message, Throwable cause) {
        super(message, cause);
    }
}
