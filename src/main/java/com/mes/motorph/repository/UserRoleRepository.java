package com.mes.motorph.repository;

import com.mes.motorph.entity.UserRole;
import com.mes.motorph.exception.UserRoleException;
import com.mes.motorph.utils.DBUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRoleRepository {

    Connection conn = null;
    Statement stmt = null;

    public List<UserRole> fetchAllUserRoles() throws UserRoleException {
        List<UserRole> userRoles = new ArrayList<>();

        try {
            conn = DBUtility.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM motorph.user_role;";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int id = rs.getInt("user_roles_id");
                int userId = rs.getInt("userId");
                int roleId = rs.getInt("roleId");

                UserRole userRole = new UserRole(id, userId, roleId);

                userRoles.add(userRole);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            throw new UserRoleException("Error connecting to database: " + e.getMessage(), e);
        } finally {
            DBUtility.closeConnection(conn);
        }
        return userRoles;
    }

    public List<UserRole> fetchUserRoles(String username) throws UserRoleException {
        List<UserRole> userRoles = new ArrayList<>();

        try {
            conn = DBUtility.getConnection();
            String sql = "SELECT e.id AS id, e.email AS Email, u.roleId AS Role FROM motorph.user_role u JOIN motorph.employee e ON u.userId = e.id   WHERE  e.email = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String uname = rs.getString("Email");
                int rId = rs.getInt("Role");

                UserRole userRole = new UserRole(id, uname, rId);
                userRoles.add(userRole);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new UserRoleException("Error connecting to database: " + e.getMessage(),e);
        } finally {
            DBUtility.closeConnection(conn);
        }
        return userRoles;
    }

    public void createUserRole(UserRole userRole) throws UserRoleException {

        try {
            conn = DBUtility.getConnection();
            String sql = "INSERT INTO motorph.user_role (userId, roleId) VALUES (?, ?);";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, userRole.getUserId());
            pstmt.setInt(2, userRole.getRoleId());

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted == 0) {
                throw new UserRoleException("Error adding new role to user.");
            } else {
                System.out.println("Added New Role to User.");
            }
        } catch (Exception e) {
            throw new UserRoleException("Error creating user role: " + e.getMessage(),e);
        } finally {
            DBUtility.closeConnection(conn);
        }
    }

    public void updateUserRole(UserRole userRole) throws UserRoleException {

        try {
            conn = DBUtility.getConnection();
            String sql = "UPDATE motorph.user_role SET userId = ?, roleId = ? WHERE user_role_id = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, userRole.getUserId());
            pstmt.setInt(2, userRole.getRoleId());
            pstmt.setInt(3, userRole.getId());

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted == 0) {
                throw new UserRoleException("Error updating role of user.");
            } else {
                System.out.println("Update user role.");
            }
        } catch (Exception e) {
            throw new UserRoleException("Error updating user role: " + e.getMessage(),e);
        } finally {
            DBUtility.closeConnection(conn);
        }
    }

    public void deleteUserRole(UserRole userRole) throws UserRoleException {

        try {
            conn = DBUtility.getConnection();
            String sql = "DELETE FROM motorph.user_role WHERE user_role_id = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, userRole.getId());


            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted == 0) {
                throw new UserRoleException("Error deleting role of user.");
            } else {
                System.out.println("Delete user role.");
            }
        } catch (Exception e) {
            throw new UserRoleException("Error deleting user role: " + e.getMessage(),e);
        } finally {
            DBUtility.closeConnection(conn);
        }
    }

    public List<UserRole> fetchAllUserRolesView() throws UserRoleException{
        List<UserRole> userRolesView = new ArrayList<>();

        try{
            conn = DBUtility.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM motorph.employee_roles;";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                int userId = rs.getInt("Employee ID");
                String email = rs.getString( "Email");
                String Roles = rs.getString("Roles");

                UserRole userRole = new UserRole(userId, email, Roles);
                userRolesView.add(userRole);
                System.out.println(userRolesView);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DBUtility.closeConnection(conn);
        }
        return userRolesView;
    }


}
