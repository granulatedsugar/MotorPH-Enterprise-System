package com.mes.motorph.repository;

import com.mes.motorph.entity.Department;
import com.mes.motorph.exception.DepartmentException;
import com.mes.motorph.utils.DBUtility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
}
