package com.mes.motorph.controller;

import com.mes.motorph.entity.*;
import com.mes.motorph.exception.*;
import com.mes.motorph.services.*;
import com.mes.motorph.utils.AlertUtility;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PayrollCreateController {
    @FXML
    private Label breadCrumb;
    @FXML
    private CheckBox overrideCheckBox;
    @FXML
    private Button runReportBtn;
    @FXML
    private TextField payrollIdField;
    @FXML
    private TextField employeeIdField;
    @FXML
    private MFXDatePicker startDatePicker;
    @FXML
    private MFXDatePicker endDatePicker;
    @FXML
    private MFXTextField employeeNameField;
    @FXML
    private MFXTextField positionField;
    @FXML
    private MFXTextField deptField;
    @FXML
    private MFXTextField monthlyRateField;
    @FXML
    private MFXTextField dailyRateField;
    @FXML
    private MFXTextField daysWorkedField;
    @FXML
    private MFXTextField overtimeField;
    @FXML
    private MFXTextField sssField;
    @FXML
    private MFXTextField phField;
    @FXML
    private MFXTextField pagIbigField;
    @FXML
    private MFXTextField withholdingTaxField;
    @FXML
    private MFXTextField riceSubField;
    @FXML
    private MFXTextField phoneAllowanceField;
    @FXML
    private MFXTextField clothingAllowanceField;
    @FXML
    private MFXTextField grossIncomeField;
    @FXML
    private MFXTextField totalDeductionsField;
    @FXML
    private MFXTextField totalBenefitsField;
    @FXML
    private MFXTextField netPayField;
    @FXML
    private MFXButton payrollButton;
    private String payrollId;
    private PayrollService payrollService = new PayrollService();
    private DeductionService deductionService = new DeductionService();
    private AttendanceService attendanceService = new AttendanceService();
    private EmployeeService employeeService = new EmployeeService();
    private PositionService positionService = new PositionService();
    private DepartmentService departmentService = new DepartmentService();
    private SalaryCalculationService salaryCalculationService = new SalaryCalculationService();
    private OvertimeService overtimeService = new OvertimeService();

    // TODO:
    // Implement next phase : continuous improvement
    // NumberFormat philippinesFormat = CurrencyUtility.getPhilippinesCurrencyFormatter();
    @FXML
    protected void initialize() {
        startDatePicker.setEditable(true);
        endDatePicker.setEditable(true);
        employeeIdField.setEditable(true);
        runReportBtn.setDisable(true);
        payrollButton.setText("Submit");
        payrollButton.setOnAction(actionEvent -> {
            onSubmitPayroll();
        });
    }

    // UPDATE data
    public void setPayrollId(String payrollId) {
        this.payrollId = payrollId;
        payrollIdField.setText(payrollId);
        breadCrumb.setText("Payroll / Update / Payslip #" + payrollId);
        startDatePicker.setDisable(true);
        endDatePicker.setDisable(true);
        employeeIdField.setEditable(false);
        payrollButton.setText("Update");
        payrollButton.setOnAction(actionEvent -> {
            onUpdatePayroll();
        });

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
        totalDeductionsField.setText(String.valueOf(totalDeductions));
        double totalBenefits = salaryCalculationService.calculateTotalBenefits(payrollEmployeeData.getAllowanceRice(), payrollEmployeeData.getAllowancePhone(),payrollEmployeeData.getTotalAllowance());
        totalBenefitsField.setText(String.valueOf(totalBenefits));
        grossIncomeField.setText(String.valueOf(payrollEmployeeData.getGrossPay()));
        netPayField.setText(String.valueOf(payrollEmployeeData.getNetPay()));
    }

    // Override IF there is no attendance record for employee
    @FXML
    protected void onClickOverride() {
        boolean isSelected = overrideCheckBox.isSelected();
        runReportBtn.setDisable(!isSelected);
    }

    // Generate new payslip
    @FXML
    protected void onClickRunReport() {
        String eid = employeeIdField.getText();

        try {
            if (eid.isEmpty() || startDatePicker.getValue() == null || endDatePicker.getValue() == null) {
                AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, "Please check the any missing information Employee ID, Start Date, End Date.");
            } else {
                Employee employee = employeeService.fetchEmployeeDetails(Integer.parseInt(eid));
                // Update UI with employee information
                updateEmployeeUI(employee);
                // Calculate salary details
                calculateSalaryDetails(employee);
            }
        } catch (EmployeeException e) {
            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, e.getMessage());
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
            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, e.getMessage());
        }
    }

//    private void calculateSalaryDetails(Employee employee) {
//        LocalDate selectedFromDate = startDatePicker.getValue();
//        LocalDate selectedToDate = endDatePicker.getValue();
//        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        String fDate = selectedFromDate.format(outputFormatter);
//        String tDate = selectedToDate.format(outputFormatter);
//        DateTimeFormatter payslipIdFormat = DateTimeFormatter.ofPattern("dd-yyyy-MM");
//        String payslipIdValue = selectedToDate.format(payslipIdFormat);
//        payrollIdField.setText(payslipIdValue + "-" + employee.getId());
//
//        try {
//            // Check Attendance
//            List<Attendance> attendances = attendanceService.fetchAttendaceByEmployeId(employee.getId(), fDate, tDate);
//            List<Overtime> overtimeList = overtimeService.fetchAllOvertimeByEmpId(employee.getId()); // This will return a list of overtime requests data logId / Date / employeeid / status(approved/pending/rejected)
//            double totalOvertime = 0;
//            int totalLate = 0;
//
//            // Count Days
//            int attendanceCount = attendances.size();
//
//            // Calculate Late and Overtime
//            for (Attendance attendance : attendances) {
//                double overtime = attendance.getOvertime();
//                totalOvertime += overtime;
//
//                int late = attendance.getLate();
//                totalLate += late;
//            }
//
//            double overtimeAmount = (employee.getHourlyRate() * 1.25 ) * totalOvertime;
//            double lateAmount = totalLate * employee.getHourlyRate();

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
            List<Overtime> overtimeList = overtimeService.fetchAllOvertimeByEmpId(employee.getId());

            double totalOvertime = 0;
            int totalLate = 0;
            int attendanceCount = attendances.size();

            // Calculate Late and Overtime
            for (Attendance attendance : attendances) {
                totalOvertime += attendance.getOvertime();
                totalLate += attendance.getLate();
            }

            // Calculate total overtime hours approved within the date range
            // Calculate total overtime hours approved within the date range
            for (LocalDate date = selectedFromDate; !date.isAfter(selectedToDate); date = date.plusDays(1)) {
                for (Overtime overtime : overtimeList) {
                    LocalDate overtimeDate = overtime.getDate().toLocalDate(); // Assuming overtime.getDate() returns a LocalDateTime
                    if (overtimeDate.isEqual(date) && overtime.getStatus().equalsIgnoreCase("Approved")) {
                        totalOvertime += 1; // Assuming each approved overtime request adds 1 hour of overtime
                    }
                }
            }

            double overtimeAmount = (employee.getHourlyRate() * 1.25 ) * totalOvertime;
            double lateAmount = totalLate * employee.getHourlyRate();

            // Create a DecimalFormat object with the desired pattern
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            // Set the rounding mode to ensure two decimal places
            decimalFormat.setRoundingMode(RoundingMode.HALF_UP);

            // Earnings
            double monthlyRate = salaryCalculationService.calculateMonthlyRate(employee.getGrossSemiMonthlyRate());
            monthlyRateField.setText(String.valueOf(monthlyRate));
            dailyRateField.setText(String.valueOf(monthlyRate / 20));
            daysWorkedField.setText(String.valueOf(attendanceCount));
            String formattedOvertimeAmount = decimalFormat.format(overtimeAmount);
            overtimeField.setText(formattedOvertimeAmount);

            // Benefits
            double totalBenefits = salaryCalculationService.calculateTotalBenefits(employee.getRiceSubsidy(), employee.getPhoneAllowance(), employee.getClothingAllowance());
            riceSubField.setText(String.valueOf(employee.getRiceSubsidy() / 2));
            phoneAllowanceField.setText(String.valueOf(employee.getPhoneAllowance() /2 ));
            clothingAllowanceField.setText(String.valueOf(employee.getClothingAllowance() / 2));

            // Gross
            double grossIncome = salaryCalculationService.calculateGrossIncome(monthlyRate, attendanceCount);
            double totalGrossIncome = grossIncome + overtimeAmount - lateAmount;

            if (attendanceCount != 0) {
                // Deductions
                double sssDeduction = deductionService.calculateSssDeduction(grossIncome);
                double phDeduction = deductionService.calculatePhilHealthDeduction(grossIncome);
                double pagIbiDeduction = deductionService.calculatePagIbigDeduction(grossIncome);
                double withholdingTax = deductionService.calculateWithholdingTax(grossIncome);
                double totalDeductions = sssDeduction + phDeduction + pagIbiDeduction + withholdingTax;

                String formattedSSS = decimalFormat.format(sssDeduction);
                String formattedPh = decimalFormat.format(phDeduction);
                String formattedPagIbig = decimalFormat.format(pagIbiDeduction);
                String formattedtotalDeductions = decimalFormat.format(totalDeductions);

                sssField.setText(formattedSSS);
                phField.setText(formattedPh);
                pagIbigField.setText(formattedPagIbig);
                // Format withholdingTax using the DecimalFormat object
                String formattedWithholdingTax = decimalFormat.format(withholdingTax);
                withholdingTaxField.setText(formattedWithholdingTax);

                totalBenefitsField.setText(String.valueOf(totalBenefits));
                String formattedGross = decimalFormat.format(totalGrossIncome);
                grossIncomeField.setText(formattedGross);
                totalDeductionsField.setText(formattedtotalDeductions);
                double totalNetPay = salaryCalculationService.calculateNetPay(totalGrossIncome, totalDeductions, totalBenefits);
                String formattedNet = decimalFormat.format(totalNetPay);
                netPayField.setText(formattedNet);

            } else {
                // Handle if no attendance data exists
                AlertUtility.showAlert(Alert.AlertType.WARNING, "No Data", null, "No attendance found for " + employee.getFirstName() + " " + employee.getLastName());
            }
        } catch (AttendanceException | PayrollException | OvertimeException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected EventHandler<ActionEvent> onSubmitPayroll() {
        processPayrollSubmission(true);
        return null;
    }

    @FXML
    protected EventHandler<ActionEvent> onUpdatePayroll() {
        processPayrollSubmission(false);
        return null;
    }

    private void processPayrollSubmission(boolean isNew) {
        try {
            Payroll payroll = fetchFromInput();
            if (isNew) {
                payrollService.createNewPayslip(payroll);
                AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Success", null, "Created new payslip.");
                // Reset Form
                resetForm();
            } else {
                payrollService.updatePayroll(payroll);
                AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Success", null, "Updated payslip.");
            }
        } catch (PayrollException e) {
            String action = isNew ? "creating" : "updating";
            String payrollId = fetchFromInput().getPayrollId();
            String errorMessage = "Error " + action + " Payroll #" + payrollId + " | Reason: " + e.getLocalizedMessage();
            AlertUtility.showAlert(Alert.AlertType.ERROR, "Error", null, errorMessage);
        }
    }

    private Payroll fetchFromInput() {
        String payrollId = payrollIdField.getText();
        int employeeId = Integer.parseInt(employeeIdField.getText());

        // Date
        LocalDate selectedFromDate = startDatePicker.getValue();
        LocalDate selectedToDate = endDatePicker.getValue();

        // Convert LocalDate to java.sql.Date using the formatter
        java.sql.Date sqlFDate = java.sql.Date.valueOf(selectedFromDate);
        java.sql.Date sqlTDate = java.sql.Date.valueOf(selectedToDate);

        // Employee Information
        String employeeName = employeeNameField.getText();
        String title = positionField.getText();
        String department = deptField.getText();

        // Earnings
        double monthlyRate = Double.parseDouble(monthlyRateField.getText());
        double semiMonthlyRate = monthlyRate / 2;
        double daysWorked = Double.parseDouble(daysWorkedField.getText());
        double overtime = Double.parseDouble(overtimeField.getText());

        // Deductions
        double sssDeduction = Double.parseDouble(sssField.getText());
        double philHealth = Double.parseDouble(phField.getText());
        double pagIbig = Double.parseDouble(pagIbigField.getText());
        double tin = 0;

        // Benefits
        double riceSub = Double.parseDouble(riceSubField.getText());
        double phoneAllowance = Double.parseDouble(phoneAllowanceField.getText());
        double clothingAllowance = Double.parseDouble(clothingAllowanceField.getText());

        // Summary
        double grossIncome = Double.parseDouble(grossIncomeField.getText());
        double netPay = Double.parseDouble(netPayField.getText());

        return new Payroll(payrollId, employeeId, sqlFDate, sqlTDate, daysWorked, clothingAllowance, phoneAllowance, riceSub, philHealth, pagIbig, tin, sssDeduction, netPay, grossIncome, employeeName, semiMonthlyRate, title, department, overtime);
    }

    protected void resetForm() {
        payrollIdField.setText("");
        employeeIdField.setText("");
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        employeeNameField.setText("");
        positionField.setText("");
        deptField.setText("");
        monthlyRateField.setText("");
        dailyRateField.setText("");
        daysWorkedField.setText("");
        overtimeField.setText("");
        sssField.setText("");
        phField.setText("");
        pagIbigField.setText("");
        withholdingTaxField.setText("");
        riceSubField.setText("");
        phoneAllowanceField.setText("");
        clothingAllowanceField.setText("");
        grossIncomeField.setText("");
        totalBenefitsField.setText("");
        totalDeductionsField.setText("");
        netPayField.setText("");
    }
}
