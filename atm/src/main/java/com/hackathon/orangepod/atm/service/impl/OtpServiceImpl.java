package com.hackathon.orangepod.atm.service.impl;

import com.hackathon.orangepod.atm.service.OtpService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class OtpServiceImpl implements OtpService {

   private static final SecureRandom random=new SecureRandom();
   private static final int OTP_LENGTH=6;

   public int generateOtp(){
       return random.nextInt(900000)+100000;
   }

   public boolean validateOtp(int actualOtp,int providedOtp){

       return String.valueOf(actualOtp).equals(String.valueOf(providedOtp));
   }
}
