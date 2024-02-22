package com.mes.motorph.entity;

import java.util.Date;

public class Payroll {
    private String payrollId;
    private int employeeId;
    private Date payPeriodFrom;
    private Date payPeriodTo;
    private double hoursWorked;
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


    public Payroll(String payrollId, int employeeId, Date payPeriodFrom, Date payPeriodTo, double hoursWorked, double allowanceClothing, double allowancePhone, double allowanceRice, double deductionPhilHealth, double deductionPagIbig, double deductionTin, double deductionSss, double netPay, double grossPay) {
        this.payrollId = payrollId;
        this.employeeId = employeeId;
        this.payPeriodFrom = payPeriodFrom;
        this.payPeriodTo = payPeriodTo;
        this.hoursWorked = hoursWorked;
        this.allowanceClothing = allowanceClothing;
        this.allowancePhone = allowancePhone;
        this.allowanceRice = allowanceRice;
        this.deductionPhilHealth = deductionPhilHealth;
        this.deductionPagIbig = deductionPagIbig;
        this.deductionTin = deductionTin;
        this.deductionSss = deductionSss;
        this.netPay = netPay;
        this.grossPay = grossPay;
    }

    public Payroll(String payrollId, int employeeId, Date payPeriodFrom, Date payPeriodTo, double hoursWorked, double totalDeduction, double totalAllowance, double grossPay, double netPay) {
        this.payrollId = payrollId;
        this.employeeId = employeeId;
        this.payPeriodFrom = payPeriodFrom;
        this.payPeriodTo = payPeriodTo;
        this.hoursWorked = hoursWorked;
        this.totalDeduction = totalDeduction;
        this.totalAllowance = totalAllowance;
        this.grossPay = grossPay;
        this.netPay = netPay;
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

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
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
}
