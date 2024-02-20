package com.mes.motorph.entity;
import java.sql.Time;
import java.sql.Date;

public class Attendance {
    private int id;
    private Date date;
    private int employeeID;
    private String firstName;
    private String lastName;
    private Time punchIn;
    private Time punchOut;
    private double workedHours;
    private int regularHours;
    private double overTime;

    public Attendance(int id,Date date, int employeeID, String firstName, String lastName, Time punchIn, Time punchOut, double workedHours, int regularHours, double overTime) {
        this.id = id;
        this.date = date;
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.punchIn = punchIn;
        this.punchOut = punchOut;
        this.workedHours = workedHours;
        this.regularHours = regularHours;
        this.overTime = overTime;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public int getEmployeeID() { return employeeID; }

    public void setEmployeeID(int employeeID) { this.employeeID = employeeID; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public Time getPunchIn() { return punchIn;  }

    public void setPunchIn(Time punchIn) { this.punchIn = punchIn; }

    public Time getPunchOut() { return punchOut; }

    public void setPunchOut(Time punchOut) {this.punchOut = punchOut; }

    public double getWorkedHours() { return workedHours;}

    public void setWorkedHours(double workedHours) { this.workedHours = workedHours; }

    public int getRegularHours() { return regularHours; }

    public void setRegularHours(int regularHours) { this.regularHours = regularHours; }

    public double getOverTime() { return overTime; }

    public void setOverTime(double overTime) { this.overTime = overTime; }
}
