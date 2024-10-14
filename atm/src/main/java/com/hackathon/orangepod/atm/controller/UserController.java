package com.hackathon.orangepod.atm.controller;

import com.hackathon.orangepod.atm.Dto.ATMResponse;
import com.hackathon.orangepod.atm.Dto.UserDto;
import com.hackathon.orangepod.atm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller

public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ATMResponse> createUser(@RequestBody UserDto userDto) {
        ATMResponse response = userService.createAccount(userDto);
        return ResponseEntity.ok(response);
    }
}
