package com.mes.motorph.controller;

import com.mes.motorph.entity.*;
import com.mes.motorph.exception.*;
import com.mes.motorph.services.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class PayrollCreateController {

    @FXML
    private Label payslipSceneTitle;
    @FXML
    private Label breadCrumb;
    @FXML
    private CheckBox overrideCheckBox;
    @FXML
    private Button runReportBtn;
    @FXML
    private Button calculateSalaryBtn;
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
    AttendanceService attendanceService = new AttendanceService();
    EmployeeService employeeService = new EmployeeService();
    PositionService positionService = new PositionService();
    DepartmentService departmentService = new DepartmentService();

    public void setPayrollId(String payrollId) {
        this.payrollId = payrollId;
        payrollIdField.setText(payrollId);

        breadCrumb.setText("Payroll / Update / Payslip #" + payrollId);
        payslipSceneTitle.setText("Update Payslip #" + payrollId);

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

    // Override IF there is no attendance record for employee
    @FXML
    protected void onClickOverride() {
        boolean isSelected = overrideCheckBox.isSelected();
        runReportBtn.setDisable(!isSelected);
        employeeNameField.setDisable(!isSelected);
        positionField.setDisable(!isSelected);
        deptField.setDisable(!isSelected);
        monthlyRateField.setDisable(!isSelected);
        riceSubField.setDisable(!isSelected);
        phoneAllowanceField.setDisable(!isSelected);
        clothingAllowanceField.setDisable(!isSelected);
        calculateSalaryBtn.setDisable(!isSelected);
    }

    @FXML
    protected void onClickRunReport() throws AttendanceException, EmployeeException, PositionException, DepartmentException {

        String eid = employeeIdField.getText();

        Employee employee = employeeService.fetchEmployeeDetails(Integer.parseInt(eid));
        List<Position> positions = positionService.fetchPositions(); // returns list of positions
        List<Department> departments = departmentService.fetchDepartments();

        // Get Empployee Position
        String employeePositionTitle = null;
        for (Position position : positions) {
            if (position.getPositionId() == employee.getPositionId()) {
                employeePositionTitle = position.getTitle();
                break;
            }
        }

        // Get Employee Departments
        String employeeDepartment = null;
        for (Department department : departments) {
            if (department.getDeptId() == employee.getDeptId()) {
                employeeDepartment = department.getDeptDesc();
                break;
            }
        }

        // Date
        LocalDate selectedFromDate = startDatePicker.getValue();
        LocalDate selectedToDate = endDatePicker.getValue();
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fDate = selectedFromDate.format(outputFormatter);
        String tDate = selectedToDate.format(outputFormatter);

        List<Attendance> attendances = attendanceService.fetchAttendaceByEmployeId(Integer.parseInt(eid), fDate, tDate);
        int attendanceCount = attendances.size();

        // Emloyee Information
        employeeNameField.setText(employee.getFirstName() + " " + employee.getLastName());
        positionField.setText(employeePositionTitle);
        deptField.setText(employeeDepartment);

        // Earnings
        double monthlyRate = employee.getGrossSemiMonthlyRate() * 2;
        monthlyRateField.setText(String.valueOf(monthlyRate));
        dailyRateField.setText(String.valueOf(monthlyRate / 20));
        daysWorkedField.setText(String.valueOf(attendanceCount));

        // Benefits
        double totalBenefits = employee.getRiceSubsidy() + employee.getPhoneAllowance() + employee.getClothingAllowance();
        riceSubField.setText(String.valueOf(employee.getRiceSubsidy()));
        phoneAllowanceField.setText(String.valueOf(employee.getPhoneAllowance()));
        clothingAllowanceField.setText(String.valueOf(employee.getClothingAllowance()));
        totalBenefitsLabels.setText(String.valueOf(totalBenefits));

        // Gross
        double grossIncome = (monthlyRate / 20) * attendanceCount;
        grossIncomeLabel.setText(String.valueOf(grossIncome));

        // Deductions
        double sssDeduction = deductionService.calculateSssDeduction(grossIncome);
        double phDeduction = deductionService.calculatePhilHealthDeduction(grossIncome);
        double pagIbiDeduction = deductionService.calculatePagIbigDeduction(grossIncome);
        double withholdingTax = deductionService.calculateWithholdingTax(grossIncome);
        double totalDeductions = sssDeduction + phDeduction + pagIbiDeduction + withholdingTax;

        sssField.setText(String.valueOf(sssDeduction));
        phField.setText(String.valueOf(phDeduction));
        pagIbigField.setText(String.valueOf(pagIbiDeduction));
        withholdingTaxField.setText(String.valueOf(withholdingTax));
        totalDeductionsLabel.setText(String.valueOf(totalDeductions));

        netPayLabel.setText(String.valueOf(grossIncome - totalDeductions + totalBenefits));
    }
}
