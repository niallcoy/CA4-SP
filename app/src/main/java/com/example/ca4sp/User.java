package com.example.ca4sp;

public class User {
    private String name;
    private String email;
    private String id;
    private String password;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String email, String id, String password) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
