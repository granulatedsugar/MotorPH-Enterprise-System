package com.mes.motorph.controller;

import com.mes.motorph.entity.Payroll;
import com.mes.motorph.exception.PayrollException;
import com.mes.motorph.services.DeductionService;
import com.mes.motorph.services.PayrollService;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PayrollCreateController {

    @FXML
    private TextField payrollIdField;
    private String payrollId;
    @FXML
    private TextField employeeIdField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextField employeeNameField;
    @FXML
    private TextField positionField;
    @FXML
    private TextField deptField;
    @FXML
    private TextField monthlyRateField;
    @FXML
    private TextField dailyRateField;
    @FXML
    private TextField daysWorkedField;
    @FXML
    private TextField sssField;
    @FXML
    private TextField phField;
    @FXML
    private TextField pagIbigField;
    @FXML
    private TextField withholdingTaxField;
    @FXML
    private TextField riceSubField;
    @FXML
    private TextField phoneAllowanceField;
    @FXML
    private TextField clothingAllowanceField;
    @FXML
    private Label grossIncomeLabel;
    @FXML
    private Label totalDeductionsLabel;
    @FXML
    private Label totalBenefitsLabels;
    @FXML
    private Label netPayLabel;



    PayrollService payrollService = new PayrollService();
    DeductionService deductionService = new DeductionService();

    public void setPayrollId(String payrollId) {
        this.payrollId = payrollId;
        payrollIdField.setText(payrollId);

        try {
            Payroll payrollEmployeeData = payrollService.fetchEmployeePayrollDetails(payrollId);

            employeeIdField.setText(String.valueOf(payrollEmployeeData.getEmployeeId()));

            // Date Picker for FROM AND TO
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            Date fromDate = payrollEmployeeData.getPayPeriodFrom();
            String formattedFromDate = formatter.format(fromDate);

            // Convert to LocalDate for FROM AND TO
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate localFromDate = LocalDate.parse(formattedFromDate, dateTimeFormatter);
            startDatePicker.setValue(localFromDate);

            Date toDate = payrollEmployeeData.getPayPeriodTo();
            String formattedToDate = formatter.format(toDate);
            LocalDate localToDate = LocalDate.parse(formattedToDate, dateTimeFormatter);
            endDatePicker.setValue(localToDate);

            // Employee Information
            employeeNameField.setText(payrollEmployeeData.getEmployeeName());
            positionField.setText(payrollEmployeeData.getPosition());
            deptField.setText(payrollEmployeeData.getDepartment());

            // Earnings
            monthlyRateField.setText(String.valueOf(payrollEmployeeData.getGrossSemiMonthlyrate() * 2));
            double dailyRateCalc = (payrollEmployeeData.getGrossSemiMonthlyrate() * 2) / 20;
            dailyRateField.setText(String.valueOf(dailyRateCalc));
            daysWorkedField.setText(String.valueOf(payrollEmployeeData.getDaysWorked()));

            // Deductions
            sssField.setText(String.valueOf(payrollEmployeeData.getDeductionSss()));
            phField.setText(String.valueOf(payrollEmployeeData.getDeductionPhilHealth()));
            pagIbigField.setText(String.valueOf(payrollEmployeeData.getDeductionPagIbig()));
            // Calculate Withholding
            double withholdingTotal = deductionService.calculateWithholdingTax(dailyRateCalc * payrollEmployeeData.getDaysWorked());
            // TODO : remove sout
            System.out.println(withholdingTotal);
            withholdingTaxField.setText(String.valueOf(withholdingTotal));

            // Benefits
            riceSubField.setText(String.valueOf(payrollEmployeeData.getAllowanceRice()));
            phoneAllowanceField.setText(String.valueOf(payrollEmployeeData.getAllowancePhone()));
            clothingAllowanceField.setText(String.valueOf(payrollEmployeeData.getAllowanceClothing()));

            // Summary
            grossIncomeLabel.setText(String.valueOf(payrollEmployeeData.getGrossPay()));
            double totalDeductions = payrollEmployeeData.getDeductionSss() + payrollEmployeeData.getDeductionPagIbig() + payrollEmployeeData.getDeductionPhilHealth();
            totalDeductionsLabel.setText(String.valueOf(totalDeductions));
            double totalBenefits = payrollEmployeeData.getTotalAllowance() + payrollEmployeeData.getAllowanceRice() + payrollEmployeeData.getAllowancePhone();
            totalBenefitsLabels.setText(String.valueOf(totalBenefits));
            netPayLabel.setText(String.valueOf(payrollEmployeeData.getNetPay()));
        } catch (PayrollException e) {
            e.printStackTrace();
        }
    }

}
