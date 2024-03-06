package com.mes.motorph.entity;
import java.sql.Time;
import java.sql.Date;

public class Attendance {
    private int id;
    private Date date;
    private int employeeId;
    private String employeeName;
    private Time timeIn;
    private Time timeOut;
    private double workedHours;
    private int regularWorkedHours;
    private int overtime;
    private int late;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public int getRegularWorkedHours() {
        return regularWorkedHours;
    }

    public void setRegularWorkedHours(int regularWorkedHours) {
        this.regularWorkedHours = regularWorkedHours;
    }

    public int getOvertime() {
        return overtime;
    }

    public void setOvertime(int overtime) {
        this.overtime = overtime;
    }

    public int getLate() {
        return late;
    }

    public void setLate(int late) {
        this.late = late;
    }

    public Attendance(int id, Date date, int employeeId, String employeeName, Time timeIn, Time timeOut, double workedHours, int regularWorkedHours, int overtime, int late) {
        this.id = id;
        this.date = date;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.workedHours = workedHours;
        this.regularWorkedHours = regularWorkedHours;
        this.overtime = overtime;
        this.late = late;
    }

    public Attendance(int id, int employeeId, Date date, Time timeIn, Time timeOut) {
        this.id = id;
        this.employeeId = employeeId;
        this.date = date;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    public Attendance(int employeeId, Date date, Time timeIn, Time timeOut) {
        this.employeeId = employeeId;
        this.date = date;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    public Attendance(int id, int employeeId, Date date, Time timeIn) {
        this.id = id;
        this.employeeId = employeeId;
        this.date = date;
        this.timeIn = timeIn;
    }

    @Override
    public String toString(){
        return id+ " " +employeeId+" "+ date +" " + timeIn;
    }
}
