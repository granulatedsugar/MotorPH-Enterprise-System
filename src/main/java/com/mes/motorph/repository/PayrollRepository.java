package com.mes.motorph.repository;

import com.mes.motorph.entity.Payroll;
import com.mes.motorph.exception.PayrollException;
import com.mes.motorph.utils.DBUtility;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class PayrollRepository {

    Connection conn = null;
    Statement stmt = null;

    public List<Payroll> fetchPayrollList() throws PayrollException {
        List<Payroll> payrolls = new ArrayList<>();

        try {
            conn = DBUtility.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM motorph.payroll_list;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String payrollId = rs.getString("PID");
                int employeeId = rs.getInt("EID");
                String employeeName = rs.getString("Employee Name");
                Date payPeriodFrom = rs.getDate("From");
                Date payPeriodTo = rs.getDate("To");
                double daysWorked = rs.getDouble("Days Worked");
                double totalDeduction = rs.getDouble("Total Deduction");
                double totalAllowance = rs.getDouble("Total Allowance");
                double grossPay = rs.getDouble("Gross Pay");
                double netPay = rs.getDouble("Net Pay");
                String department = rs.getString("Department");

                Payroll payroll = new Payroll(payrollId, employeeId, employeeName, payPeriodFrom, payPeriodTo, daysWorked, totalDeduction, totalAllowance, grossPay, netPay, department);

                payrolls.add(payroll);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            throw new PayrollException("Error connecting to database: " + e.getMessage(),e);
        } finally {
            DBUtility.closeConnection(conn);
        }
        return payrolls;
    }

    // TODO:
    // Check if Payroll has Data
    public boolean hasPayrollData() throws PayrollException, SQLException {
        try {
            conn = DBUtility.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT COUNT(*) FROM motorph.payroll_list;";
            ResultSet rs = stmt.executeQuery(sql);

            rs.next();
            int count = rs.getInt(1);

            // TODO:
//            boolean check = count > 0 ? true : false;
//            System.out.println("CHECCCCCCKKKKK MEEEEEEE!!!!!" + check);

            return count > 0;
        } catch (SQLException e) {
            throw new PayrollException("Error checking payroll data: " + e.getMessage(),e );
        } finally {
            stmt.close();
            DBUtility.closeConnection(conn);
        }
    }

    public Payroll fetchEmployeePayrollDetails(String payrollId) throws PayrollException {
        Payroll payroll = null;

        try {
            conn = DBUtility.getConnection();
            String sql = "SELECT p.*, CONCAT(e.firstname, ' ', e.lastname) AS employee_name\n" +
                    "FROM motorph.payroll p \n" +
                    "JOIN motorph.employee e ON p.employeeId = e.id\n" +
                    "WHERE p.payroll_id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, payrollId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                payroll = new Payroll(
                        rs.getString("payroll_id"),
                        rs.getInt("employeeId"),
                        rs.getDate("pay_period_from"),
                        rs.getDate("pay_period_to"),
                        rs.getDouble("days_worked"),
                        rs.getDouble("allowance_clothing"),
                        rs.getDouble("allowance_phone"),
                        rs.getDouble("allowance_rice"),
                        rs.getDouble("deduction_philhealth"),
                        rs.getDouble("deduction_pagIbig"),
                        rs.getDouble("deduction_tin"),
                        rs.getDouble("deduction_sss"),
                        rs.getDouble("net_pay"),
                        rs.getDouble("gross_pay"),
                        rs.getString("employee_name"),
                        rs.getDouble("gross_semi_monthlyrate"),
                        rs.getString("position"),
                        rs.getString("department"),
                        rs.getDouble("overtime")
                );
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new PayrollException("Error fetching employee details: " + e.getMessage(),e);
        } finally {
            DBUtility.closeConnection(conn);
        }
        return payroll;
    }

    public void  createNewPayslip(Payroll payroll) throws PayrollException {
        try {
            conn = DBUtility.getConnection();
            String sql = "INSERT INTO payroll (payroll_id, employeeId, pay_period_from, pay_period_to, days_worked, allowance_clothing, allowance_phone, allowance_rice, deduction_philhealth, deduction_pagibig, deduction_tin, deduction_sss, gross_pay, net_pay, employee_name, gross_semi_monthlyrate, `position`, department, overtime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, payroll.getPayrollId());
            pstmt.setInt(2, payroll.getEmployeeId());
            pstmt.setDate(3, (Date) payroll.getPayPeriodFrom());
            pstmt.setDate(4, (Date) payroll.getPayPeriodTo());
            pstmt.setDouble(5, payroll.getDaysWorked());
            pstmt.setDouble(6, payroll.getAllowanceClothing());
            pstmt.setDouble(7, payroll.getAllowancePhone());
            pstmt.setDouble(8, payroll.getAllowanceRice());
            pstmt.setDouble(9, payroll.getDeductionPhilHealth());
            pstmt.setDouble(10, payroll.getDeductionPagIbig());
            pstmt.setDouble(11, payroll.getDeductionTin());
            pstmt.setDouble(12, payroll.getDeductionSss());
            pstmt.setDouble(13, payroll.getGrossPay());
            pstmt.setDouble(14, payroll.getNetPay());
            pstmt.setString(15, payroll.getEmployeeName());
            pstmt.setDouble(16, payroll.getGrossSemiMonthlyrate());
            pstmt.setString(17, payroll.getPosition());
            pstmt.setString(18, payroll.getDepartment());
            pstmt.setDouble(19, payroll.getOvertime());

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted == 0) {
                throw new PayrollException("Error inserting employee into database");
            } else {
                System.out.println("Added new payslip.");
            }
        } catch (Exception e) {
            throw new PayrollException("Error connection to database: " + e.getMessage(),e);
        } finally {
            DBUtility.closeConnection(conn);
        }
    }

    public void  updatePayslip(Payroll payroll) throws PayrollException {
        try {
            conn = DBUtility.getConnection();
            String sql = "UPDATE payroll SET employeeId = ?, pay_period_from = ?, pay_period_to = ?, days_worked = ?, allowance_clothing = ?, allowance_phone = ?, allowance_rice = ?, deduction_philhealth = ?, deduction_pagibig = ?, deduction_tin = ?, deduction_sss = ?, gross_pay = ?, net_pay = ?, employee_name = ?, gross_semi_monthlyrate = ?, position = ?, department = ?, overtime = ? WHERE payroll_id = ?;";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, payroll.getEmployeeId());
            pstmt.setDate(2, (Date) payroll.getPayPeriodFrom());
            pstmt.setDate(3, (Date) payroll.getPayPeriodTo());
            pstmt.setDouble(4, payroll.getDaysWorked());
            pstmt.setDouble(5, payroll.getAllowanceClothing());
            pstmt.setDouble(6, payroll.getAllowancePhone());
            pstmt.setDouble(7, payroll.getAllowanceRice());
            pstmt.setDouble(8, payroll.getDeductionPhilHealth());
            pstmt.setDouble(9, payroll.getDeductionPagIbig());
            pstmt.setDouble(10, payroll.getDeductionTin());
            pstmt.setDouble(11, payroll.getDeductionSss());
            pstmt.setDouble(12, payroll.getGrossPay());
            pstmt.setDouble(13, payroll.getNetPay());
            pstmt.setString(14, payroll.getEmployeeName());
            pstmt.setDouble(15, payroll.getGrossSemiMonthlyrate());
            pstmt.setString(16, payroll.getPosition());
            pstmt.setString(17, payroll.getDepartment());
            pstmt.setDouble(18, payroll.getOvertime());
            pstmt.setString(19, payroll.getPayrollId());

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted == 0 ) {
                throw new PayrollException("Error updating employee into database");
            } else {
                System.out.println("Updated payslip.");
            }
        } catch (Exception e) {
            throw new PayrollException("Error connection to database: " + e.getMessage(),e);
        } finally {
            DBUtility.closeConnection(conn);
        }
    }

    public void deletePayrollById(String payrollId) throws PayrollException{
        try {
            conn = DBUtility.getConnection();
            String sql = "DELETE FROM motorph.payroll WHERE payroll_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, payrollId);

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated == 0) {
                throw new PayrollException("Error deleting employee in database.");
            } else {
                System.out.println("Deleted employee");
            }
        } catch (Exception e) {
            throw new PayrollException("Error connecting to database: " + e.getMessage(), e);
        } finally {
            DBUtility.closeConnection(conn);
        }
    }
}
