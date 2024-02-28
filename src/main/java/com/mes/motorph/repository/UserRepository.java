package com.mes.motorph.repository;

import com.mes.motorph.entity.User;
import com.mes.motorph.exception.UserException;
import com.mes.motorph.utils.DBUtility;

import java.sql.Connection;
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
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String hashPassword = rs.getString("password");

                User user = new User(id, username, hashPassword);
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
}
