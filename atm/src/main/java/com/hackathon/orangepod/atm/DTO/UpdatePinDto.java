package com.hackathon.orangepod.atm.DTO;

public class UpdatePinDto {

    private Long userId;
    private Long newPin;
    private String otp;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getNewPin() {
        return newPin;
    }

    public void setNewPin(Long newPin) {
        this.newPin = newPin;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(Long otp) {
        this.otp = String.valueOf(otp);
    }
}
