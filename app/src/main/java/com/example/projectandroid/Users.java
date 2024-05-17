package com.example.projectandroid;

public class Users {
    private String firtName;
    private String lastName;
    private String email;
    private String password;

    public Users() {
    }

    public Users(String firtName, String lastName, String email, String password) {
        this.firtName = firtName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirtName() {
        return firtName;
    }

    public void setFirtName(String firtName) {
        this.firtName = firtName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}