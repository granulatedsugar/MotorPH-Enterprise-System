package com.mes.motorph.repository;

import com.mes.motorph.entity.Employee;
import com.mes.motorph.exception.EmployeeException;
import com.mes.motorph.utils.DBUtility;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;
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

    public void createNewEmployee(Employee employee) throws EmployeeException {
        try {
            conn = DBUtility.getConnection();
            String sql = "INSERT INTO motorph.employee(address, base_salary, clothing_allowance, dateofbirth, email, firstname, gross_semi_monthlyrate, hourlyrate, lastname, pagibig, philhealth, phone_allowance, phone_number, rice_sub, sss, status, supervisor, tin, vacation_hours, sick_hours, positionId, deptId) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, employee.getAddress());
            pstmt.setDouble(2, employee.getBaseSalary());
            pstmt.setDouble(3, employee.getClothingAllowance());
            pstmt.setDate(4, employee.getDateOfBirth());
            pstmt.setString(5, employee.getEmail());
            pstmt.setString(6, employee.getFirstName());
            pstmt.setDouble(7, employee.getGrossSemiMonthlyRate());
            pstmt.setDouble(8, employee.getHourlyRate());
            pstmt.setString(9, employee.getLastName());
            pstmt.setString(10, employee.getPagIbig());
            pstmt.setString(11, employee.getPhilHealth());
            pstmt.setDouble(12, employee.getPhoneAllowance());
            pstmt.setString(13, employee.getPhoneNumber());
            pstmt.setDouble(14, employee.getRiceSubsidy());
            pstmt.setString(15, employee.getSss());
            pstmt.setString(16, employee.getStatus());
            pstmt.setString(17, employee.getSupervisor());
            pstmt.setString(18, employee.getTin());
            pstmt.setDouble(19, employee.getVacationHours());
            pstmt.setDouble(20, employee.getSickHours());
            pstmt.setInt(21, employee.getPositionId());
            pstmt.setInt(22, employee.getDeptId());

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted == 0 ) {
                throw new EmployeeException("Error adding employee into database.");
            } else {
                System.out.println("Added new employee into database.");
            }
        } catch (Exception e) {
            throw new EmployeeException("Error connecting to database: " + e.getMessage(), e);
        } finally {
            DBUtility.closeConnection(conn);
        }
    }

    public void deleteEmployee(int employeeId) throws EmployeeException {
        try {
            conn = DBUtility.getConnection();
            String sql = "DELETE FROM motorph.employee WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, employeeId);

            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted == 0) {
                throw new EmployeeException("Error deleting employee from database.");
            }else{
                System.out.println("Successfully deleted employee from database.");
            }
        } catch (Exception e) {
            throw new EmployeeException("Error deleting employee from database: " + e.getMessage(), e);
        } finally {
            DBUtility.closeConnection(conn);
        }
    }

    public void updateEmployee(Employee employee) throws EmployeeException {
        try {
            conn = DBUtility.getConnection();
            String sql = "UPDATE motorph.employee SET address=?, base_salary=?, clothing_allowance=?, dateofbirth=?, email = ?, firstname=?, gross_semi_monthlyrate=?, hourlyrate=?, lastname=?, pagibig=?, philhealth=?, phone_allowance=?, phone_number=?, rice_sub=?, sss=?, status=?, supervisor=?, tin=?, vacation_hours=?, sick_hours=?, positionId=?, deptId=? WHERE id = ?;";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, employee.getAddress());
            pstmt.setDouble(2, employee.getBaseSalary());
            pstmt.setDouble(3, employee.getClothingAllowance());
            pstmt.setDate(4, employee.getDateOfBirth());
            pstmt.setString(5, employee.getEmail());
            pstmt.setString(6, employee.getFirstName());
            pstmt.setDouble(7, employee.getGrossSemiMonthlyRate());
            pstmt.setDouble(8, employee.getHourlyRate());
            pstmt.setString(9, employee.getLastName());
            pstmt.setString(10, employee.getPagIbig());
            pstmt.setString(11, employee.getPhilHealth());
            pstmt.setDouble(12, employee.getPhoneAllowance());
            pstmt.setString(13, employee.getPhoneNumber());
            pstmt.setDouble(14, employee.getRiceSubsidy());
            pstmt.setString(15, employee.getSss());
            pstmt.setString(16, employee.getStatus());
            pstmt.setString(17, employee.getSupervisor());
            pstmt.setString(18, employee.getTin());
            pstmt.setDouble(19, employee.getVacationHours());
            pstmt.setDouble(20, employee.getSickHours());
            pstmt.setInt(21, employee.getPositionId());
            pstmt.setInt(22, employee.getDeptId());
            pstmt.setInt(23, employee.getId());

            int rowsupdated = pstmt.executeUpdate();

            if (rowsupdated == 0) {
                throw new EmployeeException("Error updating employee info in database.");
            }else{
                System.out.println("Successfully updated employee info in database.");
            }
        } catch (Exception e) {
            throw new EmployeeException("Error updating employee info in database: " + e.getMessage(), e);
        } finally {
            DBUtility.closeConnection(conn);
        }

        }
    }
