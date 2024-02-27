package com.mes.motorph.controller;

import com.mes.motorph.entity.*;
import com.mes.motorph.exception.*;
import com.mes.motorph.services.*;
import com.mes.motorph.utils.AlertUtility;
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
    private TextField overtimeField;
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

    private String payrollId;

    PayrollService payrollService = new PayrollService();
    DeductionService deductionService = new DeductionService();
    AttendanceService attendanceService = new AttendanceService();
    EmployeeService employeeService = new EmployeeService();
    PositionService positionService = new PositionService();
    DepartmentService departmentService = new DepartmentService();
    SalaryCalculationService salaryCalculationService = new SalaryCalculationService();


    // UPDATE data
    public void setPayrollId(String payrollId) {
        this.payrollId = payrollId;
        payrollIdField.setText(payrollId);
        breadCrumb.setText("Payroll / Update / Payslip #" + payrollId);
        payslipSceneTitle.setText("Update Payslip #" + payrollId);

        try {
            Payroll payrollEmployeeData = payrollService.fetchEmployeePayrollDetails(payrollId);
            setEmployeeDetails(payrollEmployeeData);
            setPayPeriodDates(payrollEmployeeData);
            setEarnings(payrollEmployeeData);
            setDeductions(payrollEmployeeData);
            setBenefitsAndSummary(payrollEmployeeData);
        } catch (PayrollException e) {
            e.printStackTrace();
        }
    }

    private void setEmployeeDetails(Payroll payrollEmployeeData) {
        employeeIdField.setText(String.valueOf(payrollEmployeeData.getEmployeeId()));
        employeeNameField.setText(payrollEmployeeData.getEmployeeName());
        positionField.setText(payrollEmployeeData.getPosition());
        deptField.setText(payrollEmployeeData.getDepartment());
    }

    private void setPayPeriodDates(Payroll payrollEmployeeData) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String formattedFromDate = formatter.format(payrollEmployeeData.getPayPeriodFrom());
        LocalDate localFromDate = LocalDate.parse(formattedFromDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        startDatePicker.setValue(localFromDate);

        String formattedToDate = formatter.format(payrollEmployeeData.getPayPeriodTo());
        LocalDate localToDate = LocalDate.parse(formattedToDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        endDatePicker.setValue(localToDate);
    }

    private void setEarnings(Payroll payrollEmployeeData) throws PayrollException {
        double grossSemiMonthly = payrollEmployeeData.getGrossSemiMonthlyrate();
        monthlyRateField.setText(String.valueOf(salaryCalculationService.calculateMonthlyRate(grossSemiMonthly)));
        double dailyRateCalc = salaryCalculationService.calculateDailyRate(grossSemiMonthly);
        dailyRateField.setText(String.valueOf(dailyRateCalc));
        daysWorkedField.setText(String.valueOf(payrollEmployeeData.getDaysWorked()));
        overtimeField.setText(String.valueOf(payrollEmployeeData.getOvertime()));
    }

    private void setDeductions(Payroll payrollEmployeeData) {
        sssField.setText(String.valueOf(payrollEmployeeData.getDeductionSss()));
        phField.setText(String.valueOf(payrollEmployeeData.getDeductionPhilHealth()));
        pagIbigField.setText(String.valueOf(payrollEmployeeData.getDeductionPagIbig()));
        double withholdingTotal = deductionService.calculateWithholdingTax(
                (payrollEmployeeData.getGrossSemiMonthlyrate() * 2 / 20) * payrollEmployeeData.getDaysWorked()
        );
        withholdingTaxField.setText(String.valueOf(withholdingTotal));
    }

    private void setBenefitsAndSummary(Payroll payrollEmployeeData) {
        riceSubField.setText(String.valueOf(payrollEmployeeData.getAllowanceRice()));
        phoneAllowanceField.setText(String.valueOf(payrollEmployeeData.getAllowancePhone()));
        clothingAllowanceField.setText(String.valueOf(payrollEmployeeData.getAllowanceClothing()));

        double totalDeductions = payrollEmployeeData.getDeductionSss()
                + payrollEmployeeData.getDeductionPagIbig()
                + payrollEmployeeData.getDeductionPhilHealth();
        totalDeductionsLabel.setText(String.valueOf(totalDeductions));
        double totalBenefits = salaryCalculationService.calculateTotalBenefits(payrollEmployeeData.getAllowanceRice(), payrollEmployeeData.getAllowancePhone(),payrollEmployeeData.getTotalAllowance());
        totalBenefitsLabels.setText(String.valueOf(totalBenefits));
        grossIncomeLabel.setText(String.valueOf(payrollEmployeeData.getGrossPay()));
        netPayLabel.setText(String.valueOf(payrollEmployeeData.getNetPay()));
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
        daysWorkedField.setDisable(!isSelected);
        riceSubField.setDisable(!isSelected);
        phoneAllowanceField.setDisable(!isSelected);
        clothingAllowanceField.setDisable(!isSelected);
        calculateSalaryBtn.setDisable(!isSelected);
    }


    // Generate new payslip
    @FXML
    protected void onClickRunReport() {
        String eid = employeeIdField.getText();

        try {
            Employee employee = employeeService.fetchEmployeeDetails(Integer.parseInt(eid));

            // Update UI with employee information
            updateEmployeeUI(employee);

            // Calculate salary details
            calculateSalaryDetails(employee);

        } catch (EmployeeException e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    // Update UI
    private void updateEmployeeUI(Employee employee) {
        try {
            List<Position> positions = positionService.fetchPositions();
            List<Department> departments = departmentService.fetchDepartments();

            String employeePositionTitle = null;
            for (Position position : positions) {
                if (position.getPositionId() == employee.getPositionId()) {
                    employeePositionTitle = position.getTitle();
                    break;
                }
            }

            String employeeDepartment = null;
            for (Department department : departments) {
                if (department.getDeptId() == employee.getDeptId()) {
                    employeeDepartment = department.getDeptDesc();
                    break;
                }
            }

            // Employee Information
            employeeNameField.setText(employee.getFirstName() + " " + employee.getLastName());
            positionField.setText(employeePositionTitle);
            deptField.setText(employeeDepartment);
        } catch (PositionException | DepartmentException e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    private void calculateSalaryDetails(Employee employee) {
        LocalDate selectedFromDate = startDatePicker.getValue();
        LocalDate selectedToDate = endDatePicker.getValue();
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fDate = selectedFromDate.format(outputFormatter);
        String tDate = selectedToDate.format(outputFormatter);
        DateTimeFormatter payslipIdFormat = DateTimeFormatter.ofPattern("dd-yyyy-MM");
        String payslipIdValue = selectedToDate.format(payslipIdFormat);
        payrollIdField.setText(payslipIdValue + "-" + employee.getId());

        try {

            // Check Attendance
            List<Attendance> attendances = attendanceService.fetchAttendaceByEmployeId(employee.getId(), fDate, tDate);
            double totalOvertime = 0;
            int totalLate = 0;

            // Count Days
            int attendanceCount = attendances.size();

            for (Attendance attendance : attendances) {
                double overtime = attendance.getOvertime();
                totalOvertime += overtime;

                int late = attendance.getLate();
                totalLate += late;
            }

            double overtimeAmount = totalOvertime;
            double lateAmount = totalLate * employee.getHourlyRate();

            // Earnings
            double monthlyRate = salaryCalculationService.calculateMonthlyRate(employee.getGrossSemiMonthlyRate());
            monthlyRateField.setText(String.valueOf(monthlyRate));
            dailyRateField.setText(String.valueOf(monthlyRate / 20));
            daysWorkedField.setText(String.valueOf(attendanceCount));

            // Benefits
            double totalBenefits = salaryCalculationService.calculateTotalBenefits(employee.getRiceSubsidy(), employee.getPhoneAllowance(), employee.getClothingAllowance());
            riceSubField.setText(String.valueOf(employee.getRiceSubsidy()));
            phoneAllowanceField.setText(String.valueOf(employee.getPhoneAllowance()));
            clothingAllowanceField.setText(String.valueOf(employee.getClothingAllowance()));

            // Gross
            double grossIncome = salaryCalculationService.calculateGrossIncome(monthlyRate, attendanceCount);
            double totalGrossIncome = grossIncome + totalOvertime - lateAmount;

            if (attendanceCount != 0) {
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

                totalBenefitsLabels.setText(String.valueOf(totalBenefits));
                grossIncomeLabel.setText(String.valueOf(grossIncome));
                totalDeductionsLabel.setText(String.valueOf(totalDeductions));
                double totalNetPay = salaryCalculationService.calculateNetPay(grossIncome, totalDeductions, totalBenefits);
                netPayLabel.setText(String.valueOf(totalNetPay));
            } else {
                // Handle if no attendance data exists
                AlertUtility.showAlert(Alert.AlertType.WARNING, "No Data", null, "No attendance found for " + employee.getFirstName() + " " + employee.getLastName());
            }
        } catch (AttendanceException | PayrollException e) {
            e.printStackTrace();
            // Handle exception
        }
    }
}
