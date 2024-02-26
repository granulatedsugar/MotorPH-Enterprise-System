package com.mes.motorph.services;

import com.mes.motorph.exception.PayrollException;

public class SalaryCalculationService {

    public double calculateMonthlyRate(double semiMonthlyRate) throws PayrollException {
        return semiMonthlyRate * 2;
    }

    public double calculateDailyRate(double monthlyRate) throws PayrollException {
        return monthlyRate / 20;
    }

    public double calculateTotalBenefits(double riceSubsidy, double phoneAllowance, double clothingAllowance) {
        return riceSubsidy + phoneAllowance + clothingAllowance;
    }

    public double calculateGrossIncome(double monthlyRate, int daysWorked) {
        return (monthlyRate / 20) * daysWorked;
    }

    public double calculateNetPay(double grossIncome, double totalDeductions, double totalBenefits) {
        return grossIncome - totalDeductions + totalBenefits;
    }
}
