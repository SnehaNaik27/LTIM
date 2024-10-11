package com.hackathon.orangepod.atm.service;

import com.hackathon.orangepod.atm.DTO.UserLogoutRequestDTO;

public class UserService {
//@Autowire
    public String logout(UserLogoutRequestDTO requestDTO){
        boolean istokenInvalid=true;
        if(istokenInvalid){
            return "Logout successful";
        }else {
            return "Logout failed: Invalid token";
        }
    }

}
