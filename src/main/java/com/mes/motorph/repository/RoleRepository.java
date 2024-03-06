package com.mes.motorph.repository;

import com.mes.motorph.entity.Role;
import com.mes.motorph.exception.RoleException;
import com.mes.motorph.utils.DBUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RoleRepository {

    Connection conn = null;
    Statement stmt = null;

    public List<Role> fetchAllRoles() throws RoleException {
        List<Role> roles = new ArrayList<>();

        try {
            conn = DBUtility.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM motorph.role;";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int roleId = rs.getInt("roleId");
                String name = rs.getString("name");

                Role role = new Role(roleId, name);
                roles.add(role);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            throw new RoleException("Error connecting to role database: " + e.getMessage(), e);
        } finally {
            DBUtility.closeConnection(conn);
        }
        return roles;
    }

    public void createRole(String name) throws RoleException {
        try {
            conn = DBUtility.getConnection();
            String sql = "INSERT INTO motorph.role (name) VALUES (?);";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, name);

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted == 0) {
                throw new RoleException("Error creating new role.");
            }
        } catch (Exception e) {
            throw new RoleException("Error creating role in database: " + e.getMessage(), e);
        } finally {
            DBUtility.closeConnection(conn);
        }
    }

    public void updateRole(int roleId, String name) throws RoleException {
        try {
            conn = DBUtility.getConnection();
            String sql = "UPDATE motorph.role SET name = ? WHERE roleId = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, name);
            pstmt.setInt(2, roleId);

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted == 0) {
                throw new RoleException("Error updating role data.");
            } else {
                System.out.println("Update role");
            }
        } catch (Exception e) {
            throw new RoleException("Error updating role in database: " + e.getMessage(),e);
        } finally {
            DBUtility.closeConnection(conn);
        }
    }

    public void deleteRole(int roleId) throws RoleException {
        try {
            conn = DBUtility.getConnection();
            String sql = "DELETE FROM motorph.role WHERE roleId = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, roleId);

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted == 0 ) {
                throw new RoleException("Error deleting role from database.");
            }
        } catch (Exception e) {
            throw new RoleException("Error deleting role in database: " + e.getMessage(), e);
        } finally {
            DBUtility.closeConnection(conn);
        }
    }
}
