package com.mes.motorph.entity;

public class User {
    private int id;
    private String username;
    private String hashPassword;

    public User(int id, String username, String hashPassword) {
        this.id = id;
        this.username = username;
        this.hashPassword = hashPassword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }
}
