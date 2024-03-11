package com.mes.motorph.entity;

import java.sql.Date;

public class Overtime {
    private int overtimeId;
    private Date date;
    private int employeeId;
    private String status;

    public Overtime(int overtimeId, Date date, int employeeId, String status) {
        this.overtimeId = overtimeId;
        this.date = date;
        this.employeeId = employeeId;
        this.status = status;
    }

    public Overtime( Date date, int employeeId, String status) {
        this.date = date;
        this.employeeId = employeeId;
        this.status = status;
    }

    public Overtime(int overtimeId, String status) {
        this.overtimeId = overtimeId;
        this.status = status;
    }
    public int getOvertimeId() {
        return overtimeId;
    }

    public void setOvertimeId(int overtimeId) {
        this.overtimeId = overtimeId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString(){
        return overtimeId + " " + date + " " + employeeId + " " + status;
    }



}
