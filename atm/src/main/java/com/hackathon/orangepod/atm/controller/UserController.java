package com.hackathon.orangepod.atm.controller;

import com.hackathon.orangepod.atm.DTO.ATMResponse;
import com.hackathon.orangepod.atm.DTO.UserDto;
import com.hackathon.orangepod.atm.DTO.UserLoginRequest;
import com.hackathon.orangepod.atm.DTO.UserLoginResponse;
import com.hackathon.orangepod.atm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atm/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ATMResponse> createUser(@RequestBody UserDto userDto) {
        ATMResponse response = userService.createUser(userDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public UserLoginResponse login (@RequestBody UserLoginRequest request){
        return userService.login(request);
    }

    @PostMapping("/logout/{userId}")
    public ResponseEntity<?> logout(@PathVariable Long userId ) {
        try {
            String logoutMessage = userService.logout(userId);
            return ResponseEntity.ok(logoutMessage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Logout failed: " + e.getMessage());
        }
    }
}

