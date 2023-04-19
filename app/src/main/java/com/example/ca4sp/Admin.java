package com.example.ca4sp;

public class Admin {

    private String name;
    private String email;
    private String password;
    private String employeeID;

    public Admin() {
        // Default constructor required for calls to DataSnapshot.getValue(Admin.class)
    }

    public Admin(String name, String email, String password, String employeeID) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.employeeID = employeeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }
}
