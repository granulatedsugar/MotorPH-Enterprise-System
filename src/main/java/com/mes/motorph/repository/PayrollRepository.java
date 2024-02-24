package com.mes.motorph.repository;

import com.mes.motorph.entity.Employee;
import com.mes.motorph.entity.Payroll;
import com.mes.motorph.exception.PayrollException;
import com.mes.motorph.utils.DBUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
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


                Payroll payroll = new Payroll(payrollId, employeeId, employeeName, payPeriodFrom, payPeriodTo, daysWorked, totalDeduction, totalAllowance, grossPay, netPay);

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

    // Check if Payroll has Data
    public boolean hasPayrollData() throws PayrollException, SQLException {
        try {
            conn = DBUtility.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT COUNT(*) FROM motorph.payroll_list;";
            ResultSet rs = stmt.executeQuery(sql);

            rs.next();
            int count = rs.getInt(1);

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
                    "JOIN motorph.employees e ON p.employeeId = e.id\n" +
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
                        rs.getString("department")
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
}
