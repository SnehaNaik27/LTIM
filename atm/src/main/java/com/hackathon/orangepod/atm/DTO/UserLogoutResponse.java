package com.hackathon.orangepod.atm.DTO;

public class UserLogoutResponse {

    private String token;
    private String message;
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}

