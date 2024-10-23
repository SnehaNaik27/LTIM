package com.hackathon.orangepod.atm.service.impl;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class OtpService {

   private static final SecureRandom random=new SecureRandom();
   private static final int OTP_LENGTH=6;

   public String generateOtp(){
       int otp=random.nextInt(900000)+100000;
       return String.valueOf(otp);
   }

   public boolean validateOtp(String actualOtp,String providedOtp){
       return actualOtp.equals(providedOtp);
   }
}
