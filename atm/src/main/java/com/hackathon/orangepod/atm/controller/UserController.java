package com.hackathon.orangepod.atm.controller;

import com.hackathon.orangepod.atm.DTO.UserLogoutRequest;
import com.hackathon.orangepod.atm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("atm/user/logout/{userId}")
    public ResponseEntity<?> logout(@PathVariable Long userId ) {

        try {
            String logoutMessage = userService.logout(userId);
            return ResponseEntity.ok(logoutMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Logout failed: " + e.getMessage());
        }

    }

}

