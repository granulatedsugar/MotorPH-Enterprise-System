package com.mes.motorph.repository;

import com.mes.motorph.entity.Employee;
import com.mes.motorph.exception.EmployeeException;
import com.mes.motorph.utils.DBUtility;

import java.sql.*;

public class EmployeeRepository {

    Connection conn = null;
    Statement stmt = null;



    public Employee fetchEmployeeDetails(int employeeId) throws EmployeeException {
        Employee employee = null;

        try {
            conn = DBUtility.getConnection();
            String sql = "SELECT * FROM motorph.employees  WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,employeeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                employee = new Employee(
                        rs.getInt("id"),
                        rs.getString("address"),
                        rs.getDouble("base_salary"),
                        rs.getDouble("clothing_allowance"),
                        rs.getDate("dateofbirth"),
                        rs.getString("email"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getDouble("gross_semi_monthlyrate"),
                        rs.getDouble("hourlyrate"),
                        rs.getString("pagibig"),
                        rs.getString("philhealth"),
                        rs.getDouble("phone_allowance"),
                        rs.getString("phone_number"),
                        rs.getDouble("rice_sub"),
                        rs.getString("sss"),
                        rs.getString("status"),
                        rs.getString("supervisor"),
                        rs.getString("tin"),
                        rs.getDouble("vacation_hours"),
                        rs.getDouble("sick_hours"),
                        rs.getInt("positionId"),
                        rs.getInt("deptId"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new EmployeeException("Error fetching employee details: " + e.getMessage(),e);
        } finally {
            DBUtility.closeConnection(conn);
        }
        return employee;
    }
}
