package com.myapplication.api.request;

import com.myapplication.api.model.User;

public class RegisterUserRequest {
    private User user;
    private String secret;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }
}
