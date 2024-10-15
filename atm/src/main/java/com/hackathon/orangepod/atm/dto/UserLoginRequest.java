package com.hackathon.orangepod.atm.DTO;

public class UserLoginRequest {

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    private String accountNumber;
    private String pin;


}
