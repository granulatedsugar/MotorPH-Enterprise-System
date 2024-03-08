package com.mes.motorph.entity;

public class UserRole {
    private int id;
    private int userId;
    private int roleId;
    private String username;
    private String email;
    private String roles;
    private String firstName;
    private String lastName;
    private String position;
    private String department;
    private int empID;
    private String empName;

    public UserRole(int id, int userId, int roleId) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
    }

    public UserRole(int empID, int roleId) {
        this.empID = empID;
        this.roleId = roleId;
    }

    public UserRole(int id, int empId, String firstName, String lastName, String email, String position, String department, String roles){
        this.id = id;
        this.empID = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.position = position;
        this.department = department;
        this.roles = roles;
    }

    public UserRole(int empID,  String empName, String username, String roles) {
        this.empID = empID;
        this.empName = empName;
        this.username = username;
        this.roles = roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    @Override
    public String toString() {
        return "Employee ID: " + empID + " / Username: " + email + " / Role ID: " + roles;
    }
}
