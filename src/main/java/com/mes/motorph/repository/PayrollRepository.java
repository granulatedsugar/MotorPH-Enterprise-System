package com.mes.motorph.repository;

import com.mes.motorph.entity.Payroll;
import com.mes.motorph.exception.PayrollException;
import com.mes.motorph.utils.DBUtility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PayrollRepository {

    Connection conn = null;
    Statement stmt = null;

    public List<Payroll> fetchWorkedHours() throws PayrollException {
        List<Payroll> payrolls = new ArrayList<>();

        try {
            conn = DBUtility.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM motorph.employee_hours";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("Log ID");
                Date date = rs.getDate("Date");
                int employeeId = rs.getInt("Employee ID");
                String employeeName = rs.getString("Employee Name");
                Time timeIn = rs.getTime("Punch In");
                Time timeOut = rs.getTime("Punch Out");
                double workedHours = rs.getDouble("Worked Hours");
                double regularHours = rs.getDouble("Regular Work Hours");
                double overTime = rs.getDouble("Overtime");

                Payroll payroll = new Payroll(id, date, employeeId, employeeName, timeIn, timeOut, workedHours, regularHours, overTime);

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
}
