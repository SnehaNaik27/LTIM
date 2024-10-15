package com.hackathon.orangepod.atm.controller;

import com.hackathon.orangepod.atm.DTO.UserLoginRequest;
import com.hackathon.orangepod.atm.DTO.UserLoginResponse;

import com.hackathon.orangepod.atm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/atm/user")
public class UserLoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login/{userlogin}")
    public UserLoginResponse login (@PathVariable String userlogin, @RequestBody UserLoginRequest request){
        return userService.login(request);
    }

}
