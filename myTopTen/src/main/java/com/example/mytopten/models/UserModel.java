package com.example.mytopten.models;

public class UserModel {

    private final long id;
    private final String username;

    public UserModel(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
