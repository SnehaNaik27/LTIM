package com.hackathon.orangepod.atm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon.orangepod.atm.DTO.AccountBalanceRequestDto;
import com.hackathon.orangepod.atm.DTO.AccountDto;
import com.hackathon.orangepod.atm.DTO.AccountOperationRequestDTO;
import com.hackathon.orangepod.atm.DTO.ReceiptDTO;
import com.hackathon.orangepod.atm.exceptions.AccountNotFoundException;
import com.hackathon.orangepod.atm.exceptions.InsufficientFundsException;
import com.hackathon.orangepod.atm.exceptions.InvalidTokenException;
import com.hackathon.orangepod.atm.exceptions.WithdrawalLimitReachedException;
import com.hackathon.orangepod.atm.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/atm/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;
    

	@Autowired
	private ObjectMapper jacksonObjectMapper;

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestParam Long userId, @RequestParam String token,
                                           @RequestParam Long amount) {
        try {
            AccountOperationRequestDTO requestDTO = AccountOperationRequestDTO.builder()
                    .userId(userId)
                    .amount(amount)
                    .token(token).build();
            AccountDto responseDto = accountService.withdraw(requestDTO);
            return ResponseEntity.status(HttpStatus.OK).body(jacksonObjectMapper.writeValueAsString(responseDto));
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

    @PostMapping("/receipt")
    public ReceiptDTO getReceipt(@RequestParam Long userId, @RequestParam String token,
                                 @RequestParam Long amount) {
       // AccountDto accountDto = accountService.withdraw(requestDto);
        return accountService.generateReceipt(userId,token,amount);
    }
    
    @PostMapping("/deposit")
	public ResponseEntity<String> deposit(@RequestParam Long userId, @RequestParam String token,
                                          @RequestParam Long amount) {

		try {
            AccountOperationRequestDTO requestDTO = AccountOperationRequestDTO.builder()
                    .userId(userId)
                    .token(token)
                    .amount(amount)
                    .build();
			AccountDto depositResponseDto = accountService.deposit(requestDTO);

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
    public ResponseEntity<String> getBalance(@RequestParam Long userId, @RequestParam String token){
        try {
            AccountOperationRequestDTO requestDTO = AccountOperationRequestDTO.builder()
                    .userId(userId)
                    .token(token).build();
            double balance = accountService.getBalance(requestDTO);
            AccountDto response = AccountDto.builder().balance(balance).build();
            return ResponseEntity.ok(jacksonObjectMapper.writeValueAsString(response));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        } catch (InvalidTokenException e) {
            return ResponseEntity.status(HttpStatus.OK).body("Invalid token. Please re-login.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    
}

