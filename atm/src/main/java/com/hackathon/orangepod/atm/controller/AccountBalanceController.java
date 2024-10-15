package com.hackathon.orangepod.atm.controller;

import com.hackathon.orangepod.atm.DTO.AccountBalanceRequestDTO;
import com.hackathon.orangepod.atm.service.AccountBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/atm/user")
public class AccountBalanceController {

    @Autowired
    private AccountBalanceService accountBalanceService;

    @GetMapping("/balance/accountBalance")
    public ResponseEntity<Double> getBalance(@RequestBody AccountBalanceRequestDTO requestDTO){

        double balance = accountBalanceService.getBalance(requestDTO);
                return ResponseEntity.ok(balance);
    }

}
