package com.hackathon.orangepod.atm.controller;

import com.hackathon.orangepod.atm.DTO.ATMResponse;
import com.hackathon.orangepod.atm.DTO.UserDto;
import com.hackathon.orangepod.atm.DTO.UserLogoutRequestDTO;
import com.hackathon.orangepod.atm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

    @Autowired
     UserService userService;

    @PostMapping("atm/user/create")
    public ResponseEntity<ATMResponse> createUser(@RequestBody UserDto userDto) {
        ATMResponse response = userService.createAccount(userDto);
        return ResponseEntity.ok(response);
    }

@PostMapping("atm/user/logout")
public ResponseEntity<?>logout(@RequestBody UserLogoutRequestDTO requestDTO){

    try{
        userService.logout(requestDTO.getToken());
        return   ResponseEntity.ok("Logout successful. Token invalidated.");
    }catch (Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Logout failed: "+e.getMessage());

    }
}
}

