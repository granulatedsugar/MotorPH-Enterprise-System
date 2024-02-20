package com.mes.motorph.entity;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

public class Payroll {
    private int id;
    private Date date;
    private int employeeId;
    private String employeeName;
    private Time timeIn;
    private Time timeOut;
    private double workedHours;
    private double regularHours;
    private double overTime;

    public Payroll(int id, Date date, int employeeId, String employeeName, Time timeIn, Time timeOut, double workedHours, double regularHours, double overTime) {
        this.id = id;
        this.date = date;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.workedHours = workedHours;
        this.regularHours = regularHours;
        this.overTime = overTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Time getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Time timeIn) {
        this.timeIn = timeIn;
    }

    public Time getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Time timeOut) {
        this.timeOut = timeOut;
    }

    public double getWorkedHours() {
        return workedHours;
    }

    public void setWorkedHours(double workedHours) {
        this.workedHours = workedHours;
    }

    public double getRegularHours() {
        return regularHours;
    }

    public void setRegularHours(double regularHours) {
        this.regularHours = regularHours;
    }

    public double getOverTime() {
        return overTime;
    }

    public void setOverTime(double overTime) {
        this.overTime = overTime;
    }
}
