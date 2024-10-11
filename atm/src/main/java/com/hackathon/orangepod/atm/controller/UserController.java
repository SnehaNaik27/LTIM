package com.hackathon.orangepod.atm.controller;

import com.hackathon.orangepod.atm.DTO.UserLogoutRequestDTO;
import com.hackathon.orangepod.atm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping
    public ResponseEntity<String>logout(@RequestBody UserLogoutRequestDTO requestDTO){
        String response= userService.logout(requestDTO);
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

}

