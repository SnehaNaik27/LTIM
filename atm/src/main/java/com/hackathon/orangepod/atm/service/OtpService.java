package com.hackathon.orangepod.atm.service;

public interface OtpService {

    public int generateOtp();

    public boolean validateOtp(int actualOtp,int providedOtp);
}
