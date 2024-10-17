package com.hackathon.orangepod.atm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon.orangepod.atm.DTO.AccountBalanceRequestDto;
import com.hackathon.orangepod.atm.DTO.AccountDto;
import com.hackathon.orangepod.atm.DTO.AccountOperationRequestDTO;
import com.hackathon.orangepod.atm.exceptions.AccountNotFoundException;
import com.hackathon.orangepod.atm.exceptions.InsufficientFundsException;
import com.hackathon.orangepod.atm.exceptions.InvalidTokenException;
import com.hackathon.orangepod.atm.exceptions.WithdrawalLimitReachedException;
import com.hackathon.orangepod.atm.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atm/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;
    

	@Autowired
	private ObjectMapper jacksonObjectMapper;

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody AccountOperationRequestDTO requestDto) {
        try {
            AccountDto responseDto = accountService.withdraw(requestDto);
            return ResponseEntity.status(HttpStatus.OK).body("Withdrawal Successful: " + responseDto);
        } catch (InvalidTokenException | WithdrawalLimitReachedException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }  catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (InsufficientFundsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
    
    @PostMapping("/deposit")
	public ResponseEntity<String> deposit(@RequestBody AccountOperationRequestDTO depositRequestDto) {

		try {
			AccountDto depositResponseDto = accountService.deposit(depositRequestDto);

			String response = jacksonObjectMapper.writeValueAsString(depositResponseDto);
			return ResponseEntity.status(HttpStatus.OK).body("Deposit Successful: " + response);
		} catch (AccountNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
		} catch (InvalidTokenException e) {
            return ResponseEntity.status(HttpStatus.OK).body("Invalid token. Please re-login.");
        } catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

    @GetMapping("/balance")
    public ResponseEntity<String> getBalance(@RequestBody AccountOperationRequestDTO requestDTO){
        try {
            double balance = accountService.getBalance(requestDTO);
            return ResponseEntity.ok(String.valueOf(balance));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        } catch (InvalidTokenException e) {
            return ResponseEntity.status(HttpStatus.OK).body("Invalid token. Please re-login.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    
}

