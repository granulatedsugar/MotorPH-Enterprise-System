package com.mes.motorph.repository;

import com.mes.motorph.entity.Employee;
import com.mes.motorph.exception.EmployeeException;
import com.mes.motorph.utils.DBUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeRepository {

    Connection conn = null;
    Statement stmt = null;



    public Employee fetchEmployeeDetails(int employeeId) throws EmployeeException {
        Employee employee = null;

        try {
            conn = DBUtility.getConnection();
            String sql = "SELECT * FROM motorph.employee WHERE id = ?";
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

    public List<Employee> fetchAllEmployees() throws EmployeeException{
        List<Employee> employees = new ArrayList<>();
        try {
            conn = DBUtility.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM motorph.employee";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String address = rs.getString("address");
                double baseSalary = rs.getDouble("base_salary");
                double clothingAllowance = rs.getDouble("clothing_allowance");
                Date dateOfBirth = rs.getDate("dateofbirth");
                String email = rs.getString("email");
                String firstName = rs.getString("firstname");
                double grossSemiMonthlyRate = rs.getDouble("gross_semi_monthlyrate");
                double hourlyRate = rs.getDouble("hourlyrate");
                String lastName = rs.getString("lastname");
                String pagIbig = rs.getString("pagibig");
                String philhealth = rs.getString("philhealth");
                double phoneAllowance = rs.getDouble("phone_allowance");
                String phoneNumber = rs.getString("phone_number");
                double riceSubsidy = rs.getDouble("rice_sub");
                String sss = rs.getString("sss");
                String status = rs.getString("status");
                String supervisor = rs.getString("supervisor");
                String tin = rs.getString("tin");
                double vacationHours = rs.getDouble("vacation_hours");
                double sickHours = rs.getDouble("sick_hours");
                int positionId = rs.getInt("positionId");
                int deptId = rs.getInt("deptId");

                Employee employee = new Employee(id,address,baseSalary,clothingAllowance,dateOfBirth,email,firstName,lastName,grossSemiMonthlyRate,hourlyRate,pagIbig,philhealth,phoneAllowance,phoneNumber,riceSubsidy,sss,status,supervisor,tin,vacationHours,sickHours,positionId,deptId);
                employees.add(employee);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new EmployeeException("Error connecting to database " + e.getMessage(), e);
        } finally {
            DBUtility.closeConnection(conn);
        }
        return employees;
    }

}
