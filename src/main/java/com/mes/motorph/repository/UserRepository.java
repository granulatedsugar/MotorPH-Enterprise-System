package com.mes.motorph.repository;

import com.mes.motorph.entity.User;
import com.mes.motorph.exception.PayrollException;
import com.mes.motorph.exception.UserException;
import com.mes.motorph.utils.DBUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    Connection conn = null;
    Statement stmt = null;

    public List<User> fetchAllUsers() throws UserException {
        List<User> users = new ArrayList<>();

        try {
            conn = DBUtility.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM motorph.user;";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                String username = rs.getString("username");
                String hashPassword = rs.getString("password");

                User user = new User(username, hashPassword);
                users.add(user);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            throw new UserException("Error connecting to database: " + e.getMessage(), e);
        } finally {
            DBUtility.closeConnection(conn);
        }
        return users;
    }

    public User fetchUserDetailPasswordReset(String username) throws UserException {
        User user = null;
        try {
            conn = DBUtility.getConnection();
            String sql = "SELECT username, password FROM motorph.user WHERE username = ? AND password = '';";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()){
                user = new User(
                        rs.getString("username"),
                        rs.getString("password"));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new UserException("Error connecting to database: " + e.getMessage(),e);
        } finally {
            DBUtility.closeConnection(conn);
        }
        return user;
    }

    public User fetchUserDetail(String username) throws UserException {
        User user = null;
        try {
            conn = DBUtility.getConnection();
            String sql = "SELECT username, password FROM motorph.user WHERE username = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()){
                user = new User(
                        rs.getString("username"),
                        rs.getString("password"));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new UserException("Error connecting to database: " + e.getMessage(),e);
        } finally {
            DBUtility.closeConnection(conn);
        }
        return user;
    }

    public void createNewUser(User user) throws UserException {

        try {
            conn = DBUtility.getConnection();
            String sql = "INSERT INTO motorph.user (username) VALUES (?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, user.getUsername());

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted == 0) {
                throw new UserException("Error adding new user.");
            } else {
                System.out.println("Added new user.");
            }
        } catch (Exception e) {
            throw new UserException("Error writing into database: " + e.getMessage(),e);
        } finally {
            DBUtility.closeConnection(conn);
        }
    }

    public void updateUser(User user) throws UserException {

        try {
            conn = DBUtility.getConnection();
            String sql = "UPDATE motorph.user SET username = ?, password = ? WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getHashPassword());
            pstmt.setString(3, user.getUsername());

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated == 0) {
                throw new PayrollException("Error updating user data.");
            } else {
                System.out.println("Updated user");
            }
        } catch (Exception e) {
            throw new UserException("Error updating user database: " + e.getMessage(), e);
        } finally {
            DBUtility.closeConnection(conn);
        }
    }

    public void deleteUser(String username) throws UserException {

        try {
            conn = DBUtility.getConnection();
            String sql = "DELETE FROM motorph.user WHERE username = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, username);

            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted == 0) {
                throw new UserException("Error deleting user from database.");
            }
        } catch (Exception e) {
            throw new UserException("Error deleting user from database: " + e.getMessage(), e);
        } finally {
            DBUtility.closeConnection(conn);
        }
    }

}
