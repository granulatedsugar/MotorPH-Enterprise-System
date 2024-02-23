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
                Date payPeriodFrom = rs.getDate("From");
                Date payPeriodTo = rs.getDate("To");
                double hoursWorked = rs.getDouble("Total Hours");
                double totalDeduction = rs.getDouble("Total Deduction");
                double totalAllowance = rs.getDouble("Total Allowance");
                double grossPay = rs.getDouble("Gross Pay");
                double netPay = rs.getDouble("Net Pay");


                Payroll payroll = new Payroll(payrollId, employeeId, payPeriodFrom, payPeriodTo, hoursWorked, totalDeduction, totalAllowance, grossPay, netPay);

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
}
