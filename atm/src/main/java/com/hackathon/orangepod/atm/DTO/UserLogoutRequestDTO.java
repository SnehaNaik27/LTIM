package com.hackathon.orangepod.atm.DTO;

public class UserLogoutRequestDTO {

    private String accountNumber;
    private String token;

    public UserLogoutRequestDTO(String accountNumber, String token) {
        this.accountNumber = accountNumber;
        this.token = token;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
