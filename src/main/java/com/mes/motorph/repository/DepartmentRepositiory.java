package com.mes.motorph.repository;

import com.mes.motorph.entity.Department;
import com.mes.motorph.exception.DepartmentException;
import com.mes.motorph.utils.DBUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepositiory {

    Connection conn = null;
    Statement stmt = null;

    public List<Department> fetchDepartments() throws DepartmentException {
        List<Department> departments = new ArrayList<>();

        try {
            conn = DBUtility.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM motorph.department;";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int id = rs.getInt("dept_id");
                String dept_desc = rs.getString("dept_desc");

                Department department = new Department(id, dept_desc);
                departments.add(department);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            throw new DepartmentException("Error connecting to database: " + e.getMessage(),e);
        } finally {
            DBUtility.closeConnection(conn);
        }
        return departments;
    }

    public void createDepartment(Department department) throws DepartmentException{
        try{
            conn = DBUtility.getConnection();
            String sql = "INSERT INTO motorph.department(dept_desc) VALUES(?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,department.getDeptDesc());

            int rows = pstmt.executeUpdate();

            if(rows == 0){
                throw new DepartmentException("Error adding the row in the database");
            }else{
                System.out.println("Department added!");
            }
        }catch (SQLException e) {
            throw new DepartmentException("Error Connecting to the database" + e.getMessage(), e);
        } finally {
            DBUtility.closeConnection(conn);
        }
    }

    public void updateDepartment(String deptDesc, int deptId) throws  DepartmentException{
        try{
            conn = DBUtility.getConnection();
            String sql = "UPDATE motorph.department SET dept_desc= ? WHERE dept_id= ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, deptDesc);
            pstmt.setInt(2, deptId);

            int rows = pstmt.executeUpdate();

            if(rows == 0){
                throw new DepartmentException("Error updating the row in the database");
            }else{
                System.out.println("Updated Row!");
            }
        }catch(SQLException e){
            throw new DepartmentException("Error Connecting to the database" + e.getMessage(), e);
        }
    }

    public void deleteDepartment(int deptid) throws DepartmentException{
        try{
            conn = DBUtility.getConnection();
            String sql = "DELETE FROM motorph.department where dept_id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, deptid);

            int row = pstmt.executeUpdate();

            if(row == 0 ){
                throw new DepartmentException("Error deleting the row in the database");
            }else{
                System.out.println("Department deleted");
            }
        }catch (SQLException e){
            throw new DepartmentException("Error Connecting to the database" + e.getMessage(), e);
        }finally {
            DBUtility.closeConnection(conn);
        }
    }

    public Department fetchDepartmentById(int deptId) throws DepartmentException {
        Department department = null;
        try {
            conn = DBUtility.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT dept_desc FROM motorph.`department` WHERE dept_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, deptId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                department = new Department(
                        rs.getString("dept_desc")
                );
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            throw new DepartmentException("Error connecting to database: " + e.getMessage(),e);
        } finally {
            DBUtility.closeConnection(conn);
        }
        return department;
    }
}
