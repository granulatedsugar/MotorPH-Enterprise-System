package com.mes.motorph.entity;

public class UserRole {
    private int id;
    private int userId;
    private int roleId;
    private String username;

    public UserRole(int id, int userId, int roleId) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
    }

    public UserRole(int userId, int roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public UserRole(int userId) {
        this.roleId = userId;
    }

    public UserRole(int id, String username, int roleId) {
        this.id = id;
        this.username = username;
        this.roleId = roleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Employee ID: " + id + " / Username: " + username + " / Role ID: " + roleId;
    }
}
