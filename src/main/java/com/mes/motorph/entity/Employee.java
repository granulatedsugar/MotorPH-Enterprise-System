package com.mes.motorph.entity;

import java.sql.Date;

public class Employee {

    private int id;
    private String address;
    private double baseSalary;
    private double clothingAllowance;
    private Date dateOfBirth;
    private String email;
    private String firstName;
    private String lastName;
    private double grossSemiMonthlyRate;
    private double hourlyRate;
    private String pagIbig;
    private String philHealth;
    private double phoneAllowance;
    private String phoneNumber;
    private double riceSubsidy;
    private String sss;
    private String status;
    private String supervisor;
    private String tin;
    private double vacationHours;
    private double sickHours;
    private int positionId;
    private int deptId;

    public Employee(int id, String address, double baseSalary, double clothingAllowance, Date dateOfBirth, String email, String firstName, String lastName, double grossSemiMonthlyRate, double hourlyRate, String pagIbig, String philHealth, double phoneAllowance, String phoneNumber, double riceSubsidy, String sss, String status, String supervisor, String tin, double vacationHours, double sickHours, int positionId, int deptId) {
        this.id = id;
        this.address = address;
        this.baseSalary = baseSalary;
        this.clothingAllowance = clothingAllowance;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.grossSemiMonthlyRate = grossSemiMonthlyRate;
        this.hourlyRate = hourlyRate;
        this.pagIbig = pagIbig;
        this.philHealth = philHealth;
        this.phoneAllowance = phoneAllowance;
        this.phoneNumber = phoneNumber;
        this.riceSubsidy = riceSubsidy;
        this.sss = sss;
        this.status = status;
        this.supervisor = supervisor;
        this.tin = tin;
        this.vacationHours = vacationHours;
        this.sickHours = sickHours;
        this.positionId = positionId;
        this.deptId = deptId;
    }

    public Employee(String address, double baseSalary, double clothingAllowance, Date dateOfBirth, String email, String firstName, String lastName, double grossSemiMonthlyRate, double hourlyRate, String pagIbig, String philHealth, double phoneAllowance, String phoneNumber, double riceSubsidy, String sss, String status, String supervisor, String tin, double vacationHours, double sickHours, int positionId, int deptId) {
        this.address = address;
        this.baseSalary = baseSalary;
        this.clothingAllowance = clothingAllowance;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.grossSemiMonthlyRate = grossSemiMonthlyRate;
        this.hourlyRate = hourlyRate;
        this.pagIbig = pagIbig;
        this.philHealth = philHealth;
        this.phoneAllowance = phoneAllowance;
        this.phoneNumber = phoneNumber;
        this.riceSubsidy = riceSubsidy;
        this.sss = sss;
        this.status = status;
        this.supervisor = supervisor;
        this.tin = tin;
        this.vacationHours = vacationHours;
        this.sickHours = sickHours;
        this.positionId = positionId;
        this.deptId = deptId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public double getClothingAllowance() {
        return clothingAllowance;
    }

    public void setClothingAllowance(double clothingAllowance) {
        this.clothingAllowance = clothingAllowance;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getGrossSemiMonthlyRate() {
        return grossSemiMonthlyRate;
    }

    public void setGrossSemiMonthlyRate(double grossSemiMonthlyRate) {
        this.grossSemiMonthlyRate = grossSemiMonthlyRate;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public String getPagIbig() {
        return pagIbig;
    }

    public void setPagIbig(String pagIbig) {
        this.pagIbig = pagIbig;
    }

    public String getPhilHealth() {
        return philHealth;
    }

    public void setPhilHealth(String philHealth) {
        this.philHealth = philHealth;
    }

    public double getPhoneAllowance() {
        return phoneAllowance;
    }

    public void setPhoneAllowance(double phoneAllowance) {
        this.phoneAllowance = phoneAllowance;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getRiceSubsidy() {
        return riceSubsidy;
    }

    public void setRiceSubsidy(double riceSubsidy) {
        this.riceSubsidy = riceSubsidy;
    }

    public String getSss() {
        return sss;
    }

    public void setSss(String sss) {
        this.sss = sss;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public double getVacationHours() {
        return vacationHours;
    }

    public void setVacationHours(double vacationHours) {
        this.vacationHours = vacationHours;
    }

    public double getSickHours() {
        return sickHours;
    }

    public void setSickHours(double sickHours) {
        this.sickHours = sickHours;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    // TODO: Delete after testing!
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", baseSalary=" + baseSalary +
                ", clothingAllowance=" + clothingAllowance +
                ", dateOfBirth=" + dateOfBirth +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", grossSemiMonthlyRate=" + grossSemiMonthlyRate +
                ", hourlyRate=" + hourlyRate +
                ", pagIbig='" + pagIbig + '\'' +
                ", philHealth='" + philHealth + '\'' +
                ", phoneAllowance=" + phoneAllowance +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", riceSubsidy=" + riceSubsidy +
                ", sss='" + sss + '\'' +
                ", status='" + status + '\'' +
                ", supervisor='" + supervisor + '\'' +
                ", tin='" + tin + '\'' +
                ", vacationHours=" + vacationHours +
                ", sickHours=" + sickHours +
                ", positionId=" + positionId +
                ", deptId=" + deptId +
                '}';
    }
    //Employee constructor without ID

}
