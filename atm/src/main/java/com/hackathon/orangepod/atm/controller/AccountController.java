package com.hackathon.orangepod.atm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon.orangepod.atm.dto.DepositRequestDto;
import com.hackathon.orangepod.atm.dto.DepositResponseDto;
import com.hackathon.orangepod.atm.exceptions.AccountNotFoundException;
import com.hackathon.orangepod.atm.exceptions.InsufficientFundsException;
import com.hackathon.orangepod.atm.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;
    

	@Autowired
	private ObjectMapper jacksonObjectMapper;

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestParam Long accountId, @RequestParam Double amount) {
        try {
            accountService.withdraw(accountId, amount);
            return ResponseEntity.ok("Withdrawal successful");
        } catch (InsufficientFundsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient funds");
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
    
    @PostMapping("/deposit")
	public ResponseEntity<String> deposit(@RequestBody DepositRequestDto depositRequestDto) {

		try {
			DepositResponseDto depositResponseDto = accountService.deposit(depositRequestDto);

			String response = jacksonObjectMapper.writeValueAsString(depositResponseDto);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (AccountNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

    
}

