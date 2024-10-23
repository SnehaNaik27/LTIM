package com.hackathon.orangepod.atm.controller;

import com.hackathon.orangepod.atm.DTO.*;
import com.hackathon.orangepod.atm.model.User;
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

    //@PutMapping("/updatePin")
    //public ResponseEntity<String>updatePin(@RequestBody UpdatePinDto updatePinDto){
      //  User updatedUser=userService.updatePin(updatePinDto);
      //  if (updatedUser !=null){
          //  return ResponseEntity.ok("PIN updated Successfully.");
       // }else {
         //   return ResponseEntity.status(404).body("User not found.");
       // }
   // }

    @PostMapping("/generateOtp")
    public String generateOtp(@RequestParam long userId){
        try {
            String otp=userService.generateAndSendOtp(userId);
            return "OTP generated and sent successfully: "+otp;
        }catch (IllegalArgumentException e){
            return e.getMessage();
        }
    }

    @PostMapping("/updatePin")
    public String updatePin(@RequestBody UpdatePinDto updatePinDto){
        try {
            userService.updatePin(updatePinDto);
            return "Pin updated Successfully";
        }catch (IllegalArgumentException e){
            return e.getMessage();
        }
    }


}

