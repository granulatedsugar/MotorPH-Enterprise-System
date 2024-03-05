package com.mes.motorph.entity;

import javafx.collections.ObservableList;

import java.util.Date;

public class Payroll {
    private String payrollId;
    private int employeeId;
    private Date payPeriodFrom;
    private Date payPeriodTo;
    private double daysWorked;
    private double allowanceClothing;
    private double allowancePhone;
    private double allowanceRice;
    private double deductionPhilHealth;
    private double deductionPagIbig;
    private double deductionTin;
    private double deductionSss;
    private double netPay;
    private double grossPay;
    private double totalDeduction;
    private double totalAllowance;
    private String employeeName;
    private double grossSemiMonthlyrate;
    private String position;
    private String department;
    private double overtime;

    public Payroll(String payrollId, int employeeId, Date payPeriodFrom, Date payPeriodTo, double daysWorked, double allowanceClothing, double allowancePhone, double allowanceRice, double deductionPhilHealth, double deductionPagIbig, double deductionTin, double deductionSss, double netPay, double grossPay, String employeeName, double grossSemiMonthlyrate, String position, String department, double overtime) {
        this.payrollId = payrollId;
        this.employeeId = employeeId;
        this.payPeriodFrom = payPeriodFrom;
        this.payPeriodTo = payPeriodTo;
        this.daysWorked = daysWorked;
        this.allowanceClothing = allowanceClothing;
        this.allowancePhone = allowancePhone;
        this.allowanceRice = allowanceRice;
        this.deductionPhilHealth = deductionPhilHealth;
        this.deductionPagIbig = deductionPagIbig;
        this.deductionTin = deductionTin;
        this.deductionSss = deductionSss;
        this.netPay = netPay;
        this.grossPay = grossPay;
        this.employeeName = employeeName;
        this.grossSemiMonthlyrate = grossSemiMonthlyrate;
        this.position = position;
        this.department = department;
        this.overtime = overtime;
    }

    public Payroll(String payrollId, int employeeId, String employeeName, Date payPeriodFrom, Date payPeriodTo, double daysWorked, double totalDeduction, double totalAllowance, double grossPay, double netPay, String department) {
        this.payrollId = payrollId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.payPeriodFrom = payPeriodFrom;
        this.payPeriodTo = payPeriodTo;
        this.daysWorked = daysWorked;
        this.totalDeduction = totalDeduction;
        this.totalAllowance = totalAllowance;
        this.grossPay = grossPay;
        this.netPay = netPay;
        this.department = department;
    }

    public String getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(String payrollId) {
        this.payrollId = payrollId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Date getPayPeriodFrom() {
        return payPeriodFrom;
    }

    public void setPayPeriodFrom(Date payPeriodFrom) {
        this.payPeriodFrom = payPeriodFrom;
    }

    public Date getPayPeriodTo() {
        return payPeriodTo;
    }

    public void setPayPeriodTo(Date payPeriodTo) {
        this.payPeriodTo = payPeriodTo;
    }

    public double getDaysWorked() {
        return daysWorked;
    }

    public void setDaysWorked(double daysWorked) {
        this.daysWorked = daysWorked;
    }

    public double getAllowanceClothing() {
        return allowanceClothing;
    }

    public void setAllowanceClothing(double allowanceClothing) {
        this.allowanceClothing = allowanceClothing;
    }

    public double getAllowancePhone() {
        return allowancePhone;
    }

    public void setAllowancePhone(double allowancePhone) {
        this.allowancePhone = allowancePhone;
    }

    public double getAllowanceRice() {
        return allowanceRice;
    }

    public void setAllowanceRice(double allowanceRice) {
        this.allowanceRice = allowanceRice;
    }

    public double getDeductionPhilHealth() {
        return deductionPhilHealth;
    }

    public void setDeductionPhilHealth(double deductionPhilHealth) {
        this.deductionPhilHealth = deductionPhilHealth;
    }

    public double getDeductionPagIbig() {
        return deductionPagIbig;
    }

    public void setDeductionPagIbig(double deductionPagIbig) {
        this.deductionPagIbig = deductionPagIbig;
    }

    public double getDeductionTin() {
        return deductionTin;
    }

    public void setDeductionTin(double deductionTin) {
        this.deductionTin = deductionTin;
    }

    public double getDeductionSss() {
        return deductionSss;
    }

    public void setDeductionSss(double deductionSss) {
        this.deductionSss = deductionSss;
    }

    public double getNetPay() {
        return netPay;
    }

    public void setNetPay(double netPay) {
        this.netPay = netPay;
    }

    public double getGrossPay() {
        return grossPay;
    }

    public void setGrossPay(double grossPay) {
        this.grossPay = grossPay;
    }

    public double getTotalDeduction() {
        return totalDeduction;
    }

    public void setTotalDeduction(double totalDeduction) {
        this.totalDeduction = totalDeduction;
    }

    public double getTotalAllowance() {
        return totalAllowance;
    }

    public void setTotalAllowance(double totalAllowance) {
        this.totalAllowance = totalAllowance;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public double getGrossSemiMonthlyrate() {
        return grossSemiMonthlyrate;
    }

    public void setGrossSemiMonthlyrate(double grossSemiMonthlyrate) {
        this.grossSemiMonthlyrate = grossSemiMonthlyrate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getOvertime() {
        return overtime;
    }

    public void setOvertime(double overtime) {
        this.overtime = overtime;
    }

    // Testing predicate results
    // TODO : Remove for later!
    @Override
    public String toString() {
        return "Payroll{" +
                "payrollId=" + payrollId +
                ", employeeId=" + employeeId +
                ", payPeriodFrom=" + payPeriodFrom +
                ", payPeriodTo=" + payPeriodTo +
                ", daysWorked=" + daysWorked +
                ", allowanceClothing=" + allowanceClothing +
                ", allowancePhone=" + allowancePhone +
                ", allowanceRice=" + allowanceRice +
                ", deductionPhilHealth=" + deductionPhilHealth +
                ", deductionPagIbig=" + deductionPagIbig +
                ", deductionTin=" + deductionTin +
                ", deductionSss=" + deductionSss +
                ", netPay=" + netPay +
                ", grossPay=" + grossPay +
                '}';
    }

}
