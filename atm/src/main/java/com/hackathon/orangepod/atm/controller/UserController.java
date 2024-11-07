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

@CrossOrigin(origins = "http://localhost:4200")
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


    @PostMapping("/check-pin")
    public String checkPin(@RequestBody UserLoginRequest request) {
        return userService.checkPin(request);
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

    @GetMapping("/validateLogin")
    public boolean validateLogin (@RequestBody UserLoginRequest request){
        return userService.validateLogin(request);
    }

    @GetMapping("/validateContactNumber")
    public boolean validateContactNumber (@RequestBody UserDto request){
        return userService.validateContactNumber(request);
    }

    @PostMapping("/generateOtp")
    public String generateOtp(@RequestParam long userId){
        try {
            int otp = userService.generateAndSendOtp(userId);
            return "OTP generated and sent successfully: "+otp;
        }catch (IllegalArgumentException e){
            return e.getMessage();
        }
    }
}

